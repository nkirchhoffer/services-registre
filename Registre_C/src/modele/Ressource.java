package modele;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Ressource {
	private int i;

	public Ressource(){
		this(0);
	}
	
	public Ressource(int j) {
		this.i = j;
	}

	@XmlElement(name="i")
	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}
	
}
