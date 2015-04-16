package de.evonomy.evolution;

import java.util.LinkedList;

import main.SkillElement;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SkillElementView extends LinearLayout {
	private ImageButton button;
	private SkillElement element;
	private LinkedList<SkillElementView> childs;
	private boolean clickable = true;
	private Context context;
	private SkillTreeFragment frago;
	private LinkedList<SkillElement> skills;
	// is set to false, if other zweig of parent is skilled
	private boolean isSkillable;
	private boolean childIsSkilled;

	public SkillElementView(final Context context, final SkillElement element,
			final SkillTreeFragment frago,LinkedList<SkillElement> skills) {
		super(context);
		this.element = element;
		this.context = context;
		this.frago = frago;
		this.skills=skills;
		isSkillable = true;
		childIsSkilled=false;
		inflate(context, R.layout.skill_element_view, this);

		childs = new LinkedList<SkillElementView>();
		button = (ImageButton) findViewById(R.id.button_skill_element_view);
		if (!((GameActivity) context).isSkilled(element.getUpdate())) {
			this.setAlpha(0.5f);
			if (element.getParent() != null
					&& !((GameActivity) context).isSkilled(element.getParent()
							.getUpdate())){
				clickable = false;
				
			}

		}
		for(SkillElement ski:skills){
			if(((GameActivity) context).isSkilled(ski.getUpdate()) && 
					ski.getParent()!= null &&
					ski.getParent().getUpdate()
					==element.getUpdate()){
				clickable=false;
				childIsSkilled=true;
			}
		}
		setImage();
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (clickable && isSkillable) {
					// TODO start the Dialog to display all the information
					SkillDialogFragment frag = SkillDialogFragment.newInstance(
							element.getUpdate(),
							((GameActivity) context).getSpecies(),
							element.getPrice(), frago);

					FragmentManager fm = ((GameActivity) context)
							.getSupportFragmentManager();
					frag.show(fm, "fragment_skill_dialog");

				}
				else if(childIsSkilled){
					Toast.makeText(context,
							context.getString(
							R.string.fragment_skill_dialog_not_backsillable),
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	public SkillElement getSkillElement() {
		return element;
	}

	public void addChildren(SkillElementView sk) {
		childs.add(sk);
	}

	public LinkedList<SkillElementView> getChilds() {
		return childs;
	}

	public float getAnchorX() {
		return getX() + (1f / 2f)
				* getResources().getDimension(R.dimen.skill_element_hw);
	}

	public float getAnchorBottomY() {
		return getY() + getResources().getDimension(R.dimen.skill_element_hw);
	}

	public float getAnchorTopY() {
		return getY();

	}

	private void setImage() {
		// switch by possibleupdate typ for getting da right picta
		// TODO irgendeiner muss hier noch die bimbo arbeit machen
		// Also die bilder setzen
		int id = ((R.drawable.ic_launcher));
		switch (element.getUpdate()) {
		case BATTLEWINGS:
			id = ((R.drawable.battlewings));
			break;
		case BETTERMUSCLES:
			id = ((R.drawable.muscle));

			break;
		case BRAIN:
			id = ((R.drawable.brain));
			break;
		case CENTRALNERVSYSTEM:
			id = ((R.drawable.centralnerv));
			break;
		case CLAWARM:
			id = ((R.drawable.claw));
			break;
		case COMPLEXTENDONSTRUCTUR:
			id = ((R.drawable.muscle));
			break;
		case DECOTAIL:
			id = ((R.drawable.decotail));
			break;
		case DRAGONSCALE:
			id = ((R.drawable.dragonscale));
			break;
		case EXTREMITYARM:
			id = ((R.drawable.extemity));
			break;
		case EXTREMITYLEG:
			id = ((R.drawable.extemity));
			break;
		case EYES:
			id = ((R.drawable.eyes));
			break;
		case FIGHTTAIL:
			id = ((R.drawable.fighttail));
			break;
		case FATLAYER:
			id = ((R.drawable.fatlayer));
			break;
		case FINLEG:
			id = ((R.drawable.finextr));
			break;
		case FLYWINGS:
			id = ((R.drawable.flywings));
			break;
		case FOOTARM:
			id = ((R.drawable.foot));
			break;
		case FIREMAKING:
			id = ((R.drawable.fire));
			break;
		case FOOTLEG:
			id = ((R.drawable.foot));
			break;
		case FRONTALLOBE:
			id = ((R.drawable.frontal_lobe));
			break;
		case FURLESSSKIN:
			id = ((R.drawable.furlessskin));
			break;
		case GENITAL:
			id = ((R.drawable.eggs));
			break;
		case GILLS:
			id = ((R.drawable.water));
			break;
		case GYMNASTICTAIL:
			id = ((R.drawable.agilitytail));
			break;
		case HANDARM:
			id = ((R.drawable.hand));
			break;
		case HANDLEG:
			id = ((R.drawable.hand));
			break;
		case KIDSCHEME:
			id = ((R.drawable.kidscheme));
			break;
		case LANGUAGE:
			id = ((R.drawable.language));
			break;
		case LEATHERSKIN:
			id = ((R.drawable.fatlayer));
			break;
		case LIMBICSYSTEM:
			id = ((R.drawable.limbic));
			break;
		case LOGIC:
			id = ((R.drawable.logic));
			break;
		case MAVERICK:
			id = ((R.drawable.maverick));
			break; 
		case MONOGAMY:
			id = ((R.drawable.monogamy));
			break;
		case PACKANIMAL:
			id = ((R.drawable.packanimal));
			break;
		case PHEROMONS:
			id = ((R.drawable.pheromons));
			break;
		case SECONDGENITAL:
			id = ((R.drawable.eggs));
			break;
		case SETTLE:
			id = ((R.drawable.settle));
			break;
		case SEXUALPROCREATION:
			id = ((R.drawable.sexualprocreation));
			break;
		case SPITFIREDRAGON:
			id = ((R.drawable.spitfire));
			break;
		case SWEATGLAND:
			id = ((R.drawable.sweat));
			break;
		case TAIL:
			id = ((R.drawable.decotail));
			break;
		case THUMBS:
			id = ((R.drawable.thumbs));
			break;
		case ULTRASAOUND:
			id = ((R.drawable.ultrasound));
			break;
		case WINGS:
			id = ((R.drawable.wing));
			break;
		case POLYGAMY:
			id = ((R.drawable.polygamy));
			break;

		default:
			id = ((R.drawable.ic_launcher));
		}
		int heightwidth =(int) getResources().getDimension(
				R.dimen.skill_element_hw)- (int) getResources().getDimension(
						R.dimen.skill_element_padding);
		//TODO check
//		button.setImageDrawable(new BitmapDrawable(decodeSampledBitmapFromResource(getResources(),
//				id, heightwidth, heightwidth)));
		button.setImageBitmap(Bitmap.createScaledBitmap(decodeSampledBitmapFromResource(getResources(),
				id, heightwidth, heightwidth),heightwidth,heightwidth,false));
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	public void branchUnskillable() {
		isSkillable = false;
		for (SkillElementView cu : childs) {
			cu.branchUnskillable();
		}
	}

	public boolean isSkillable() {
		return isSkillable;
	}

}
