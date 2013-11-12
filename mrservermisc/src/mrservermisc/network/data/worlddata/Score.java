package mrservermisc.network.data.worlddata;

import javax.xml.bind.annotation.XmlElement;

public class Score {

    @XmlElement(name="yellow")
	int mYellowTeam;
    
    @XmlElement(name="blue")
	int mBlueTeam;

	@Override
	public String toString() {
		return "Score [mYellowTeam=" + mYellowTeam + ", mBlueTeam=" + mBlueTeam
				+ "]";
	}
    
}