package main;

public class SkillLogic {
	public static SkillValue getSkillValue(PossibleUpdates update,boolean reverse){
		SkillValue s=new SkillValue();
		switch (update) {
		case LANDSPECIES:
			s.setWater(false);
			s.setIntelligence(s.getIntelligence()+5);
			break;
		case WATESPECIES:
			s.setWater(true);
			s.setIntelligence(s.getIntelligence()-5);
			break;
		case CARNIVORE:
			s.setStrength(s.getStrength()+5);
			s.setResourceDemand(s.getResourceDemand()+5);
			break;
		case HERBIVORE:
			s.setStrength(s.getStrength()-5);
			s.setResourceDemand(s.getResourceDemand()-5);
			break;
		case ENDOSKELETON:
			s.setStrength(s.getStrength()-3);
			s.setAgility(s.getAgility()+3);
			s.setMovementChance(s.getMovementChance()+0.05);
			break;
		case EXOSKELETON:
			s.setStrength(s.getStrength()+3);
			s.setAgility(s.getAgility()-3);
			s.setMovementChance(s.getMovementChance()-0.05);
			break;
		case RSTRATEGIST:
			s.setProcreation(s.getProcreation()+5);
			s.setStrength(s.getStrength()-5);
			break;
		case KSTRATEGIST:
			s.setProcreation(s.getProcreation()-5);
			s.setStrength(s.getStrength()+5);
			break;
		case THINFUR:
			s.setMinTemp(s.getMinTemp()+20);
			s.setMaxTemp(s.getMaxTemp()+20);
			break;
		case THICKFUR:
			s.setMinTemp(s.getMinTemp()-20);
			s.setMaxTemp(s.getMaxTemp()-20);
			break;
		case BATTLEWINGS:
			s.setStrength(s.getStrength()+12);
			s.setResourceDemand(s.getResourceDemand()+7);
			break;
		case WINGS:
			s.setAgility(s.getAgility()+8);
			break;
		case FLYWINGS:
			s.setAgility(s.getAgility()+4);
			s.setMovementChance(s.getMovementChance()+0.3);
			break;
		case CENTRALNERVSYSTEM:
			s.setIntelligence(s.getIntelligence()+2);
			s.setSocial(s.getSocial()+2);
			break;
		case GILLS:
			s.setWater(true);
			break;
		case BRAIN:
			s.setIntelligence(s.getIntelligence()+5);
			s.setSocial(s.getSocial()+5);
			break;
		case FRONTALLOBE:
			s.setIntelligence(s.getIntelligence()+25);
			break;
		case LIMBICSYSTEM:
			s.setSocial(s.getSocial()+25);
			break;
		case ULTRASAOUND:
			s.setVisibillity(s.getVisibillity()+20);
			break;
		case EYES:
			s.setVisibillity(s.getVisibillity()+40);
			break;
		case LEATHERSKIN:
			s.setMaxTemp(s.getMaxTemp()+2);
			s.setMinTemp(s.getMinTemp()-2);
			break;
		case SWEATGLAND:
			s.setMinTemp(s.getMinTemp()-5);
			s.setMaxTemp(s.getMaxTemp()+10);
			break;
		case FATLAYER:
			s.setMinTemp(s.getMinTemp()-15);
			s.setResourceDemand(s.getResourceDemand()+5);
			break;
		case FURLESSSKIN:
			s.setMaxTemp(s.getMaxTemp()+30);
			break;
		case BETTERMUSCLES:
			s.setAgility(s.getAgility()+5);
			s.setStrength(s.getStrength()+5);
			break;
		case DRAGONSCALE:
			s.setStrength(s.getStrength()+25);
			break;
		case COMPLEXTENDONSTRUCTUR:
			s.setAgility(s.getAgility()+25);
			break;
		case CLAWARM:
			s.setStrength(s.getStrength()+15);
			break;
		case EXTREMITYARM:
			break;
		case HANDARM:
			s.setAgility(s.getAgility()+15);
			break;
		case FOOTARM:
			s.setMovementChance(s.getMovementChance()+0.2);
			break;
		case HANDLEG:
			s.setAgility(s.getAgility()+15);
			break;
		case EXTREMITYLEG:
			break;
		case FOOTLEG:
			s.setMovementChance(s.getMovementChance()+0.2);
			break;
		case FINLEG:
			s.setWater(true);
			s.setMovementChance(s.getMovementChance()+0.2);
			break;
		case GENITAL:
			s.setProcreation(s.getProcreation()+5);
			break;
		case SECONDGENITAL:
			s.setProcreation(s.getProcreation()+20);
			break;
		case TAIL:
			s.setAgility(s.getAgility()+2);
			s.setStrength(s.getStrength()+2);
			break;
		case DECOTAIL:
			s.setSocial(s.getSocial()+20);
			s.setProcreation(s.getProcreation()+5);
			break;
		case FIGHTTAIL:
			s.setStrength(s.getStrength()+15);
			break;
		case GYMNASTICTAIL:
			s.setAgility(s.getAgility()+15);
			break;
		case FIREMAKING:
			s.setMinTemp(s.getMinTemp()-5);
			s.setResourceDemand(s.getResourceDemand()-5);
			break;
		case KIDSCHEME:
			s.setProcreation(s.getProcreation()+5);
			break;
		case LANGUAGE:
			s.setSocial(s.getSocial()+10);
			break;
		case LOGIC:
			s.setIntelligence(s.getIntelligence()+10);
			break;
		case MAVERICK:
			s.setProcreation(s.getProcreation()-6);
			s.setSocial(s.getSocial()-10);
			s.setStrength(s.getStrength()+8);
			s.setAgility(s.getAgility()+8);
			break;
		case PACKANIMAL:
			s.setProcreation(s.getProcreation()+6);
			s.setSocial(s.getSocial()+10);
			s.setStrength(s.getStrength()-8);
			s.setAgility(s.getAgility()-8);
			break;
		case MONOGAMY:
			s.setProcreation(s.getProcreation()-5);
			s.setSocial(s.getSocial()+5);
			break;
		case POLYGAMY:
			s.setProcreation(s.getProcreation()+5);
			s.setSocial(s.getSocial()-5);
			break;
		case THUMBS:
			s.setAgility(s.getAgility()+5);
			break;
		case PHEROMONS:
			s.setSocial(s.getSocial()+3);
			s.setProcreation(s.getProcreation()+5);
			break;
		case SETTLE:
			s.setMaxTemp(s.getMaxTemp()+10);
			s.setMinTemp(s.getMinTemp()-10);
			break;
		case SEXUALPROCREATION:
			s.setProcreation(s.getProcreation()+7);
			break;
		case SPITFIREDRAGON:
			s.setStrength(s.getStrength()+25);
			s.setResourceDemand(s.getResourceDemand()+15);
			s.setMinTemp(s.getMinTemp()-7);
			break;
		default:
			throw new RuntimeException("Type is not valid");
		}
		if(reverse){
			s.setAgility(s.getAgility()*-1);
			s.setIntelligence(s.getIntelligence()*-1);
			s.setMaxTemp(s.getMaxTemp()*-1);
			s.setMinTemp(s.getMinTemp()*-1);
			s.setMovementChance(s.getMovementChance()*-1);
			s.setProcreation(s.getProcreation()*-1);
			s.setResourceDemand(s.getResourceDemand()*-1);
			s.setSocial(s.getSocial()*-1);
			s.setStrength(s.getStrength()*-1);
			s.setVisibillity(s.getVisibillity()*-1);
			if(s.isChangesWater())
				s.setWater(!s.isWater());
		}
		return s;
	}
	public static int getSkillCosts(PossibleUpdates update,boolean reverse){
		SkillValue v=getSkillValue(update, reverse);
		/*
		 * Here is the formula to calculate the costs of a skill
		 * */
		int sum=0;
		sum+=Math.abs(v.getAgility());
		sum+=Math.abs(v.getIntelligence());
		sum+=Math.abs(v.getMaxTemp());
		sum+=Math.abs(v.getMinTemp());
		sum+=Math.abs(v.getMovementChance());
		sum+=Math.abs(v.getProcreation());
		sum+=Math.abs(v.getResourceDemand());
		sum+=Math.abs(v.getSocial());
		sum+=Math.abs(v.getStrength());
		sum+=Math.abs(v.getVisibillity());
		sum+=Math.abs(v.isChangesWater()?25:0);
		return (int)Math.pow(sum, 1.5);
		
		
		
	}
}
