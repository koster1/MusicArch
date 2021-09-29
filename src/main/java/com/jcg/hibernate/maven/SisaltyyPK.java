package com.jcg.hibernate.maven;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class SisaltyyPK implements Serializable {
	@Basic(optional = false)
	@Column(name="Järjestysnumero");
	

}
