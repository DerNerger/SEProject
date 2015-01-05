package gameProtocol;

import main.AreaLandTypeChange;
import main.AreaPopulationChange;
import main.Change;
import main.FieldChange;
import main.PointsTimeChange;
import main.PopulationChange;
import main.SpeciesUpdate;
import main.VisibilityChange;

public class ChangePacketFactuary {
	public static Change parseChange(String str){
		if(str.startsWith("SpeciesUpdate"))
			return SpeciesUpdate.parseSpeciesUpdate(str);
		else if(str.startsWith("FieldChange"))
			return FieldChange.parseSpeciesUpdate(str);
		else if(str.startsWith("PopulationChange"))
			return PopulationChange.parsePopulationChange(str);
		else if(str.startsWith("AreaPopulationChange"))
			return AreaPopulationChange.parseAreaPopulationChange(str);
		else if(str.startsWith("AreaLandTypeChange"))
			return AreaLandTypeChange.parseAreaLandTypeChange(str);
		else if(str.startsWith("PointsTimeChange"))
			return PointsTimeChange.parsePointsTimeChange(str);
		else if(str.startsWith("VisibilityChange"))
			return VisibilityChange.parseVisibilityChange(str);
		else
			throw new RuntimeException(str+" kann nicht geparset werden typ ist unbekannt");
	}
}
