package com.spotonresponse.xchangecore;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.NamespaceContext;

class SimpleNamespaceContext implements NamespaceContext {
	private Map<String, String> urisByPrefix = new HashMap<String, String>();

	private Map<String, Set<String>> prefixesByURI = new HashMap<String, Set<String>>();

	public SimpleNamespaceContext() {
		addNamespace("xml", "http://www.w3.org/XML/1998/namespace");
		addNamespace("xmlns", "http://www.w3.org/2000/xmlns/");
	}

	public synchronized void addNamespace(String prefix, String namespaceURI) {
		this.urisByPrefix.put(prefix, namespaceURI);
		if (this.prefixesByURI.containsKey(namespaceURI)) {
			((Set<String>) this.prefixesByURI.get(namespaceURI)).add(prefix);
		} else {
			Set<String> set = new HashSet<String>();
			set.add(prefix);
			this.prefixesByURI.put(namespaceURI, set);
		}
	}

	public String getNamespaceURI(String prefix) {
		if (prefix == null)
			throw new IllegalArgumentException("prefix cannot be null");
		if (this.urisByPrefix.containsKey(prefix)) {
			return (String) this.urisByPrefix.get(prefix);
		}
		return "";
	}

	public String getPrefix(String namespaceURI) {
		return (String) getPrefixes(namespaceURI).next();
	}

	@SuppressWarnings("unchecked")
	public Iterator<String> getPrefixes(String namespaceURI) {
		if (namespaceURI == null)
			throw new IllegalArgumentException("namespaceURI cannot be null");
		if (this.prefixesByURI.containsKey(namespaceURI)) {
			return ((Set<String>) this.prefixesByURI.get(namespaceURI))
					.iterator();
		}
		return Collections.EMPTY_SET.iterator();
	}
}