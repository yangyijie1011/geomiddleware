//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.12.28 at 09:45:38 AM ICT 
//


package geomobility.core.gateway.mlp.entity.svc_result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tlrr_event complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tlrr_event">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{}ms_action"/>
 *         &lt;element ref="{}change_area"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tlrr_event", propOrder = {
    "msAction",
    "changeArea"
})
public class TlrrEvent {

    @XmlElement(name = "ms_action")
    protected MsAction msAction;
    @XmlElement(name = "change_area")
    protected ChangeArea changeArea;

    /**
     * Gets the value of the msAction property.
     * 
     * @return
     *     possible object is
     *     {@link MsAction }
     *     
     */
    public MsAction getMsAction() {
        return msAction;
    }

    /**
     * Sets the value of the msAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link MsAction }
     *     
     */
    public void setMsAction(MsAction value) {
        this.msAction = value;
    }

    /**
     * Gets the value of the changeArea property.
     * 
     * @return
     *     possible object is
     *     {@link ChangeArea }
     *     
     */
    public ChangeArea getChangeArea() {
        return changeArea;
    }

    /**
     * Sets the value of the changeArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChangeArea }
     *     
     */
    public void setChangeArea(ChangeArea value) {
        this.changeArea = value;
    }

}
