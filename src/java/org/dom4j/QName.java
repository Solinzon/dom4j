/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id$
 */

package org.dom4j;

import org.dom4j.tree.QNameCache;

/** <p><code>QName</code> represents a qualified name value of an XML element 
  * or attribute. It consists of a local name and a {@link Namespace} 
  * instance. This object is immutable.</p>
  *
  * @author <a href="mailto:james.strachan@metastuff.com">James Strachan</a>
  * @version $Revision$
  */
public class QName {

    protected static QNameCache cache = new QNameCache();
    
    
    /** The local name of the element or attribute */
    private String name;
    
    /** The qualified name of the element or attribute */
    private String qualifiedName;
    
    /** The Namespace of this element or attribute */
    private Namespace namespace;
    
    /** A cached version of the hashcode for efficiency */
    private int hashCode;
    

    public static synchronized QName get(String name) {
        return cache.get(name);
    }
    
    public static synchronized QName get(String name, Namespace namespace) {
        return cache.get(name, namespace);
    }
    
    public QName(String name) {
        this( name, Namespace.NO_NAMESPACE );
    }
    
    public QName(String name, Namespace namespace) {
        this.name = (name == null) ? "" : name;
        this.namespace = (namespace == null) ? Namespace.NO_NAMESPACE : namespace;
    }

    
    /** @return the local name
      */
    public String getName() {
        return name;
    }
    
    /** @return the qualified name in the format <code>prefix:localName</code>
      */
    public String getQualifiedName() {
        if ( qualifiedName == null ) {
            String prefix = getNamespacePrefix();
            if ( prefix != null && prefix.length() > 0 ) {
                qualifiedName = prefix + ":" + name;
            }
            else {
                qualifiedName = name;
            }
        }
        return qualifiedName;
    }
    
    /** @return the namespace of this QName 
      */
    public Namespace getNamespace() {
        return namespace;
    }
        
    /** @return the namespace URI of this QName
      */
    public String getNamespacePrefix() {
        if ( namespace == null ) {
            return "";
        }
        return namespace.getPrefix();
    }
        
    /** @return the namespace URI of this QName
      */
    public String getNamespaceURI() {
        if ( namespace == null ) {
            return "";
        }
        return namespace.getURI();
    }
        
    
    /** @return the hash code based on the qualified name and the URI of the 
      * namespace.
      */
    public int hashCode() {
        if ( hashCode == 0 ) {
            hashCode = getQualifiedName().hashCode() 
                ^ getNamespaceURI().hashCode();
            if ( hashCode == 0 ) {
                hashCode = 0xbabe;
            }
        }
        return hashCode;
    }
  
    public boolean equals(Object object) {
        if ( this == object ) {
            return true;
        }
        else if ( object instanceof QName ) {
            QName that = (QName) object;
            // we cache hash codes so this should be quick
            if ( hashCode() == that.hashCode() ) {
                return getQualifiedName().equals( that.getQualifiedName() )
                    && getNamespaceURI().equals( that.getNamespaceURI());
            }
        }
        return false;
    }
}




/*
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "DOM4J" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of MetaStuff, Ltd.  For written permission,
 *    please contact dom4j-info@metastuff.com.
 *
 * 4. Products derived from this Software may not be called "DOM4J"
 *    nor may "DOM4J" appear in their names without prior written
 *    permission of MetaStuff, Ltd. DOM4J is a registered
 *    trademark of MetaStuff, Ltd.
 *
 * 5. Due credit should be given to the DOM4J Project
 *    (http://dom4j.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY METASTUFF, LTD. AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * METASTUFF, LTD. OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 *
 * $Id$
 */