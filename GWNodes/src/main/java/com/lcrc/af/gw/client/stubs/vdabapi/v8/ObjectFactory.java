
package com.lcrc.af.gw.client.stubs.vdabapi.v8;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.lcrc.af.gw.client.stubs.vdabapi.v8 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _TransactionId_QNAME = new QName("http://guidewire.com/ws/soapheaders", "transaction_id");
    private final static QName _Locale_QNAME = new QName("http://guidewire.com/ws/soapheaders", "locale");
    private final static QName _Authentication_QNAME = new QName("http://guidewire.com/ws/soapheaders", "authentication");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.lcrc.af.gw.client.stubs.vdabapi.v8
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAvailableCommands }
     * 
     */
    public GetAvailableCommands createGetAvailableCommands() {
        return new GetAvailableCommands();
    }

    /**
     * Create an instance of {@link GetCommandUIInfoResponse }
     * 
     */
    public GetCommandUIInfoResponse createGetCommandUIInfoResponse() {
        return new GetCommandUIInfoResponse();
    }

    /**
     * Create an instance of {@link ControlUIInfo }
     * 
     */
    public ControlUIInfo createControlUIInfo() {
        return new ControlUIInfo();
    }

    /**
     * Create an instance of {@link Execute }
     * 
     */
    public Execute createExecute() {
        return new Execute();
    }

    /**
     * Create an instance of {@link WsiAuthenticationException }
     * 
     */
    public WsiAuthenticationException createWsiAuthenticationException() {
        return new WsiAuthenticationException();
    }

    /**
     * Create an instance of {@link GetAvailableCommandsResponse }
     * 
     */
    public GetAvailableCommandsResponse createGetAvailableCommandsResponse() {
        return new GetAvailableCommandsResponse();
    }

    /**
     * Create an instance of {@link ExecuteResponse }
     * 
     */
    public ExecuteResponse createExecuteResponse() {
        return new ExecuteResponse();
    }

    /**
     * Create an instance of {@link GetAvailableObjects }
     * 
     */
    public GetAvailableObjects createGetAvailableObjects() {
        return new GetAvailableObjects();
    }

    /**
     * Create an instance of {@link GetAvailableObjectsResponse }
     * 
     */
    public GetAvailableObjectsResponse createGetAvailableObjectsResponse() {
        return new GetAvailableObjectsResponse();
    }

    /**
     * Create an instance of {@link GetCommandUIInfo }
     * 
     */
    public GetCommandUIInfo createGetCommandUIInfo() {
        return new GetCommandUIInfo();
    }

    /**
     * Create an instance of {@link Authentication }
     * 
     */
    public Authentication createAuthentication() {
        return new Authentication();
    }

    /**
     * Create an instance of {@link Locale }
     * 
     */
    public Locale createLocale() {
        return new Locale();
    }

    /**
     * Create an instance of {@link TransactionId }
     * 
     */
    public TransactionId createTransactionId() {
        return new TransactionId();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransactionId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://guidewire.com/ws/soapheaders", name = "transaction_id")
    public JAXBElement<TransactionId> createTransactionId(TransactionId value) {
        return new JAXBElement<TransactionId>(_TransactionId_QNAME, TransactionId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Locale }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://guidewire.com/ws/soapheaders", name = "locale")
    public JAXBElement<Locale> createLocale(Locale value) {
        return new JAXBElement<Locale>(_Locale_QNAME, Locale.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Authentication }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://guidewire.com/ws/soapheaders", name = "authentication")
    public JAXBElement<Authentication> createAuthentication(Authentication value) {
        return new JAXBElement<Authentication>(_Authentication_QNAME, Authentication.class, null, value);
    }

}
