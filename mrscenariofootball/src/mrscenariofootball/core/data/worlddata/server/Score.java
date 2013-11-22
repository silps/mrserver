package mrscenariofootball.core.data.worlddata.server;

import javax.xml.bind.annotation.XmlElement;

public class Score {

    @XmlElement(name="yellow")
	int mYellowTeam = 0;
    
    @XmlElement(name="blue")
	int mBlueTeam = 0;
    
    public Score() {}
    
	public Score( Score aScore ) {
		
		mYellowTeam = aScore.getScoreYellowTeam();
		mBlueTeam = aScore.getScoreBlueTeam();

	}
	
	@Override
	public String toString() {
		return "Score [mYellowTeam=" + mYellowTeam + ", mBlueTeam=" + mBlueTeam
				+ "]";
	}

	public int getScoreYellowTeam() {
		return mYellowTeam;
	}

	public int getScoreBlueTeam() {
		return mBlueTeam;
	}

	public void setScoreYellowTeam( int aNewScore ) {
		mYellowTeam = aNewScore;
	}

	public void setScoreBlueTeam( int aNewScore ) {
		mBlueTeam = aNewScore;
	}
    
}
