
package com.lcrc.af.gw.client.stubs.vdabapi.v7;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ControlUIInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ControlUIInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FilterOpts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NoArgs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ParamOpts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ScopeOpts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ControlUIInfo", namespace = "http://example.com/gw/acc/vdab/web", propOrder = {
    "filterOpts",
    "noArgs",
    "paramOpts",
    "scopeOpts"
})
public class ControlUIInfo {

    @XmlElement(name = "FilterOpts")
    protected String filterOpts;
    @XmlElement(name = "NoArgs")
    protected int noArgs;
    @XmlElement(name = "ParamOpts")
    protected String paramOpts;
    @XmlElement(name = "ScopeOpts")
    protected String scopeOpts;

    /**
     * Gets the value of the filterOpts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilterOpts() {
        return filterOpts;
    }

    /**
     * Sets the value of the filterOpts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilterOpts(String value) {
        this.filterOpts = value;
    }

    /**
     * Gets the value of the noArgs property.
     * 
     */
    public int getNoArgs() {
        return noArgs;
    }

    /**
     * Sets the value of the noArgs property.
     * 
     */
    public void setNoArgs(int value) {
        this.noArgs = value;
    }

    /**
     * Gets the value of the paramOpts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParamOpts() {
        return paramOpts;
    }

    /**
     * Sets the value of the paramOpts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParamOpts(String value) {
        this.paramOpts = value;
    }

    /**
     * Gets the value of the scopeOpts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScopeOpts() {
        return scopeOpts;
    }

    /**
     * Sets the value of the scopeOpts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScopeOpts(String value) {
        this.scopeOpts = value;
    }

}
