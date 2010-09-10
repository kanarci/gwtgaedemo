package cz.cvut.felk.via.examples.datastore.client.rpcobjects;

import java.io.Serializable;

import com.google.gwt.core.client.GWT;

/**
 * Subject RPC is container class designed to ease the manipulation with db
 * objects on the client-side. This class needs to implement the serialization
 * and have zero-argument constructor. Otherwise the RPC call will fail.
 * 
 * @author Floatrix
 * 
 */
@SuppressWarnings("serial")
public class SubjectRPC implements Serializable {

	private String key;

	private String name;

	private String code;

	public SubjectRPC(String name, String code) {
		super();
		// only client side
		assert GWT.isClient();
		this.name = name;
		this.code = code;
	}

	/**
	 * @param key
	 * @param name
	 * @param code
	 */
	public SubjectRPC(String key, String name, String code) {
		super();
		// only server side
		assert !GWT.isClient();
		this.key = key;
		this.name = name;
		this.code = code;
	}

	/**
	 * Default constructor - serialization required
	 */
	SubjectRPC() {
		super();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
