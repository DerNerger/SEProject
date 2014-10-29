/**
 * @author Felix Kibellus
 *In einem Objekt der Klasse Change, werden aenderungen vermerkt welche
* ein Feld erfahren hat. Dieses Objekt sollte zum Controller
* durchgerecht werden damit dieser die Aenderungen in ein IPlayer eintragen
* kann.
* */
public abstract class Change {
	public abstract void doChange(IPlayer player);
}
