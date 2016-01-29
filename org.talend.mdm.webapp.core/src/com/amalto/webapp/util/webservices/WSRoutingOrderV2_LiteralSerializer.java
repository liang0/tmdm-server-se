// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation 
// Generated source version: 1.1.2

package com.amalto.webapp.util.webservices;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.xsd.XSDConstants;
import com.sun.xml.rpc.encoding.literal.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;
import java.util.List;
import java.util.ArrayList;

public class WSRoutingOrderV2_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final QName ns1_name_QNAME = new QName("", "name");
    private static final QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final QName ns1_status_QNAME = new QName("", "status");
    private static final QName ns2_WSRoutingOrderV2Status_TYPE_QNAME = new QName("urn-com-amalto-xtentis-webservice", "WSRoutingOrderV2Status");
    private CombinedSerializer ns2myns2_WSRoutingOrderV2Status__WSRoutingOrderV2Status_LiteralSerializer;
    private static final QName ns1_timeCreated_QNAME = new QName("", "timeCreated");
    private static final QName ns3_long_TYPE_QNAME = SchemaConstants.QNAME_TYPE_LONG;
    private CombinedSerializer ns3_myns3__long__long_Long_Serializer;
    private static final QName ns1_timeScheduled_QNAME = new QName("", "timeScheduled");
    private static final QName ns1_timeLastRunStarted_QNAME = new QName("", "timeLastRunStarted");
    private static final QName ns1_timeLastRunCompleted_QNAME = new QName("", "timeLastRunCompleted");
    private static final QName ns1_wsItemPK_QNAME = new QName("", "wsItemPK");
    private static final QName ns2_WSItemPK_TYPE_QNAME = new QName("urn-com-amalto-xtentis-webservice", "WSItemPK");
    private CombinedSerializer ns2_myWSItemPK_LiteralSerializer;
    private static final QName ns1_serviceJNDI_QNAME = new QName("", "serviceJNDI");
    private static final QName ns1_serviceParameters_QNAME = new QName("", "serviceParameters");
    private static final QName ns1_message_QNAME = new QName("", "message");
    private static final QName ns1_bindingUniverseName_QNAME = new QName("", "bindingUniverseName");
    private static final QName ns1_bindingUserToken_QNAME = new QName("", "bindingUserToken");
    
    public WSRoutingOrderV2_LiteralSerializer(QName type, String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public WSRoutingOrderV2_LiteralSerializer(QName type, String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns3_string_TYPE_QNAME);
        ns2myns2_WSRoutingOrderV2Status__WSRoutingOrderV2Status_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.amalto.webapp.util.webservices.WSRoutingOrderV2Status.class, ns2_WSRoutingOrderV2Status_TYPE_QNAME);
        ns3_myns3__long__long_Long_Serializer = (CombinedSerializer)registry.getSerializer("", long.class, ns3_long_TYPE_QNAME);
        ns2_myWSItemPK_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.amalto.webapp.util.webservices.WSItemPK.class, ns2_WSItemPK_TYPE_QNAME);
    }
    
    public Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.amalto.webapp.util.webservices.WSRoutingOrderV2 instance = new com.amalto.webapp.util.webservices.WSRoutingOrderV2();
        Object member=null;
        QName elementName;
        List values;
        Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_name_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_name_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setName((java.lang.String)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_name_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_status_QNAME)) {
                member = ns2myns2_WSRoutingOrderV2Status__WSRoutingOrderV2Status_LiteralSerializer.deserialize(ns1_status_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setStatus((com.amalto.webapp.util.webservices.WSRoutingOrderV2Status)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_status_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_timeCreated_QNAME)) {
                member = ns3_myns3__long__long_Long_Serializer.deserialize(ns1_timeCreated_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setTimeCreated(((Long)member).longValue());
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_timeCreated_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_timeScheduled_QNAME)) {
                member = ns3_myns3__long__long_Long_Serializer.deserialize(ns1_timeScheduled_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setTimeScheduled(((Long)member).longValue());
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_timeScheduled_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_timeLastRunStarted_QNAME)) {
                member = ns3_myns3__long__long_Long_Serializer.deserialize(ns1_timeLastRunStarted_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setTimeLastRunStarted(((Long)member).longValue());
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_timeLastRunStarted_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_timeLastRunCompleted_QNAME)) {
                member = ns3_myns3__long__long_Long_Serializer.deserialize(ns1_timeLastRunCompleted_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setTimeLastRunCompleted(((Long)member).longValue());
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_timeLastRunCompleted_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_wsItemPK_QNAME)) {
                member = ns2_myWSItemPK_LiteralSerializer.deserialize(ns1_wsItemPK_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setWsItemPK((com.amalto.webapp.util.webservices.WSItemPK)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_wsItemPK_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_serviceJNDI_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_serviceJNDI_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setServiceJNDI((java.lang.String)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_serviceJNDI_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_serviceParameters_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_serviceParameters_QNAME, reader, context);
                instance.setServiceParameters((java.lang.String)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_serviceParameters_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_message_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_message_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setMessage((java.lang.String)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_bindingUniverseName_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_bindingUniverseName_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setBindingUniverseName((java.lang.String)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_bindingUserToken_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_bindingUserToken_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setBindingUserToken((java.lang.String)member);
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (Object)instance;
    }
    
    public void doSerializeAttributes(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.webapp.util.webservices.WSRoutingOrderV2 instance = (com.amalto.webapp.util.webservices.WSRoutingOrderV2)obj;
        
    }
    public void doSerialize(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.webapp.util.webservices.WSRoutingOrderV2 instance = (com.amalto.webapp.util.webservices.WSRoutingOrderV2)obj;
        
        if (instance.getName() == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getName(), ns1_name_QNAME, null, writer, context);
        if (instance.getStatus() == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns2myns2_WSRoutingOrderV2Status__WSRoutingOrderV2Status_LiteralSerializer.serialize(instance.getStatus(), ns1_status_QNAME, null, writer, context);
        if (new Long(instance.getTimeCreated()) == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns3_myns3__long__long_Long_Serializer.serialize(new Long(instance.getTimeCreated()), ns1_timeCreated_QNAME, null, writer, context);
        if (new Long(instance.getTimeScheduled()) == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns3_myns3__long__long_Long_Serializer.serialize(new Long(instance.getTimeScheduled()), ns1_timeScheduled_QNAME, null, writer, context);
        if (new Long(instance.getTimeLastRunStarted()) == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns3_myns3__long__long_Long_Serializer.serialize(new Long(instance.getTimeLastRunStarted()), ns1_timeLastRunStarted_QNAME, null, writer, context);
        if (new Long(instance.getTimeLastRunCompleted()) == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns3_myns3__long__long_Long_Serializer.serialize(new Long(instance.getTimeLastRunCompleted()), ns1_timeLastRunCompleted_QNAME, null, writer, context);
        if (instance.getWsItemPK() == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns2_myWSItemPK_LiteralSerializer.serialize(instance.getWsItemPK(), ns1_wsItemPK_QNAME, null, writer, context);
        if (instance.getServiceJNDI() == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getServiceJNDI(), ns1_serviceJNDI_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getServiceParameters(), ns1_serviceParameters_QNAME, null, writer, context);
        if (instance.getMessage() != null) {
            ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getMessage(), ns1_message_QNAME, null, writer, context);
        }
        if (instance.getBindingUniverseName() != null) {
            ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getBindingUniverseName(), ns1_bindingUniverseName_QNAME, null, writer, context);
        }
        if (instance.getBindingUserToken() != null) {
            ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getBindingUserToken(), ns1_bindingUserToken_QNAME, null, writer, context);
        }
    }
}