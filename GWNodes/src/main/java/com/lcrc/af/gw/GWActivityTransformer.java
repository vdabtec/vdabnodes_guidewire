package com.lcrc.af.gw;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import com.lcrc.af.AnalysisObject;


public class GWActivityTransformer implements ClassFileTransformer {
		boolean c_InitCompleted;
		public byte[] transform(ClassLoader loader, String className,
				Class classBeingRedefined, ProtectionDomain protectionDomain,
				byte[] classfileBuffer) throws IllegalClassFormatException {
			byte[] byteCode = classfileBuffer;

			// since this transformer will be called when all the classes are

			if (className.equals("rules/EventMessage/EventFired")) {
				if (!c_InitCompleted){
					AnalysisObject.logError("0>>>>>>>>>>>>>>>>>>>>GWActivityBase.transform()","Initializing GWActivityAgent");
					GWActivityAgent.setVDABLocation("127.0.0.1:31205");
					GWActivityAgent.setReportInterval(10L);
					GWActivityAgent.start();
					c_InitCompleted = true;
				}
				AnalysisObject.logError("1>>>>>>>>>>>>>>>>>>>>>GWActivityBase.transform()","Instrumenting Event Message rules");
				try {
					ClassPool classPool = ClassPool.getDefault();
					CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(
							classfileBuffer));
					CtMethod[] methods = ctClass.getDeclaredMethods();
					for (CtMethod method : methods) {
						if (method.getName().equals("invoke")){
							if (method.getSignature().indexOf("Session;Lcom") > 0){
								AnalysisObject.logError("2>>>>>>>>>>>>>>>>>>>>GWActivityBase.transform()","Inserted a method");
								method.insertBefore("com.lcrc.af.gw.GWActivityAgent.pushActivityData(bean.getEventName());");
								method.insertAfter("com.lcrc.af.AnalysisObject.logError(\" >>>>>>>>>>>>>>>>>>>)\",bean.getEventName());");						}
							
						}
							/*
						method.addLocalVariable("startTime", CtClass.longType);
						method.insertBefore("startTime = System.nanoTime();");
						method.insertAfter("System.out.println(\"Execution Duration "
								+ "(nano sec): \"+ (System.nanoTime() - startTime) );");
						*/
					}
					byteCode = ctClass.toBytecode();
					ctClass.detach();
					System.out.println("Instrumentation complete.");
				} catch (Throwable ex) {
					System.out.println("Exception: " + ex);
					ex.printStackTrace();
				}
			}
			return byteCode;
		}
}
