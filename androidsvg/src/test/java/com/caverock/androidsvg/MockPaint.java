package com.caverock.androidsvg;

import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Shader;
import android.graphics.Typeface;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadow.api.Shadow;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Paul on 10/07/2017.
 */

@Implements(Paint.class)
public class MockPaint
{
   public static final String ALPHA = "alpha";
   public static final String COLOR = "color";
   public static final String FLAGS = "flags";
   public static final String HINTING = "hinting";
   public static final String PATHEFFECT = "pathEffect";
   public static final String SHADER = "shader";
   public static final String STRIKETHRU = "strikeThruText";
   public static final String STROKECAP = "strokeCap";
   public static final String STROKEJOIN = "strokeJoin";
   public static final String STROKEWIDTH = "strokeWidth";
   public static final String STYLE = "style";
   public static final String TEXTSIZE = "textSize";
   public static final String TYPEFACE = "typeface";
   public static final String UNDERLINETEXT = "underlineText";
   public static final String FONTFEATURES = "fontFeatures";
   public static final String FONTVARIATION = "fontVariation";

   public LinkedHashMap<String, String> settings = new LinkedHashMap<>();

   @Implementation
   public void __constructor__()
   {
   }

   @Implementation
   public void __constructor__(Paint paint)
   {
      this.settings.putAll(((MockPaint) Shadow.extract(paint)).settings);
   }

   @Implementation
   public void __constructor__(int flags)
   {
      if (flags != 0)
         settings.put(FLAGS, "f:" + genFlagsVal(flags));
   }

   @Implementation
   public void setAlpha(int alpha)
   {
      settings.remove(ALPHA);
      settings.put(ALPHA, "a:" + alpha);
   }


   @Implementation
   public void setColor(int color)
   {
      settings.remove(COLOR);
      settings.put(COLOR, "color:#" + String.format("%08x", color));
   }

   @Implementation
   public void setFlags(int flags)
   {
      settings.remove(FLAGS);
      if (flags != 0)
         settings.put(FLAGS, "f:" + genFlagsVal(flags));
   }

   @Implementation
   public void setHinting(int hinting)
   {
      settings.remove(HINTING);
      settings.put(HINTING, "h:" + ((hinting == Paint.HINTING_ON) ? "ON" : "OFF"));
   }

   @Implementation
   public void setPathEffect(PathEffect pathEffect)
   {
      settings.remove(PATHEFFECT);
      settings.put(PATHEFFECT, "dash:" + pathEffect);
   }

   @Implementation
   public void setShader(Shader shader)
   {
      settings.remove(SHADER);
      settings.put(SHADER, "grad:" + shader);
   }

   @Implementation
   public void setStrikeThruText(boolean strikeThruText)
   {
      settings.remove(STRIKETHRU);
      if (strikeThruText)
         settings.put(STRIKETHRU, "<s>");
   }

   @Implementation
   public void setStrokeCap(Paint.Cap strokeCap)
   {
      settings.remove(STROKECAP);
      settings.put(STROKECAP, "cap:" + strokeCap);
   }

   @Implementation
   public void setStrokeJoin(Paint.Join join)
   {
      settings.remove(STROKEJOIN);
      settings.put(STROKEJOIN, "join:" + join);
   }

   @Implementation
   public void setStrokeWidth(float strokeWidth)
   {
      settings.remove(STROKEWIDTH);
      settings.put(STROKEWIDTH, "sw:" + num(strokeWidth));
   }

   @Implementation
   public void setStyle(Paint.Style style)
   {
      settings.remove(STYLE);
      settings.put(STYLE, "s:" + style);
   }

   @Implementation
   public void setTextSize(float textSize)
   {
      settings.remove(TEXTSIZE);
      settings.put(TEXTSIZE, "ts:" + num(textSize));
   }

   @Implementation
   public void setTypeface(Typeface typeface)
   {
      settings.remove(TYPEFACE);
      settings.put(TYPEFACE, "tf:" + typeface);
   }

   @Implementation
   public void setUnderlineText(boolean underlineText)
   {
      settings.remove(UNDERLINETEXT);
      if (underlineText)
         settings.put(UNDERLINETEXT, "<u>");
   }

   @Implementation
   public void setFontFeatureSettings(String features)
   {
      settings.remove(FONTFEATURES);
      settings.put(FONTFEATURES, "ff:" + features);
   }

   @Implementation
   public void setFontVariationSettings(String variation)
   {
      settings.remove(FONTVARIATION);
      settings.put(FONTVARIATION, "fv:" + variation);
   }


   //-----------------------------------------------------------------------------------------------


   String  getDescription()
   {
      return "Paint(" + String.join("; ", settings.values()) + ")";
   }

   public static String  num(float f)
   {
      if (f == (long) f)
         return String.format("%d", (long) f);
      else
         return String.format("%s", round(f, 2));
   }

   public static float round(float value, int places)
   {
      BigDecimal bd = new BigDecimal(Float.toString(value));
      bd = bd.setScale(places, RoundingMode.HALF_UP);
      return bd.floatValue();
   }

   public String  genFlagsVal(int flags)
   {
      List<String> f = new LinkedList<>();
      for (int b=0; b<= 31; b++) {
         int m = 1 << b;
         if ((flags & m) != 0) {
            switch (m) {
               case Paint.ANTI_ALIAS_FLAG: f.add("ANTI_ALIAS"); break;
               case Paint.LINEAR_TEXT_FLAG: f.add("LINEAR_TEXT"); break;
               case Paint.SUBPIXEL_TEXT_FLAG: f.add("SUBPIXEL_TEXT"); break;
               case Paint.FILTER_BITMAP_FLAG: f.add("FILTER_BITMAP"); break;
               default: f.add(String.valueOf(b)); break;
            }
         }
      }
      return String.join("|", f);
   }

}
