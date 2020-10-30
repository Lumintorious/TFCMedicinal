package com.lumintorious.tfcmedicinal;

import net.dries007.tfc.ConfigTFC;
import net.minecraftforge.common.config.Config;
import com.lumintorious.tfcmedicinal.TFCMedicinal.*;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = "tfcmedicinal", category = "")
@Config.LangKey("config.tfcmedicinal")
public class TFCMedicinalConfig
{
    final static String MODID = "tfcmedicinal";

    @Config.RequiresWorldRestart
    @Config.Comment("Client settings")
    @Config.LangKey("config." + MODID + ".general")
    public static final GeneralClass General = new GeneralClass();

    public static class GeneralClass
    {
        @Config.RequiresWorldRestart
        @Config.Comment("How many eyes does a spider have?.")
        @Config.LangKey("config." + MODID + ".general.spiderEyeNumber")
        @Config.RangeInt(min = 1, max = 8)
        public int spiderEyeNumber = 4;

        @Config.RequiresWorldRestart
        @Config.Comment("Nutrition level after death, 4 = normal, 1 = none?")
        @Config.LangKey("config." + MODID + ".general.nutritionAfterDeath")
        @Config.RangeInt(min = 1, max = 4)
        public int nutritionAfterDeath = 1;

        @Config.RequiresWorldRestart
        @Config.Comment("How many ticks does the death sickness effect last if not removed by an antidote.")
        @Config.LangKey("config." + MODID + ".general.deathSicknessDuration")
        public int deathSicknessDuration = 24000;

        @Config.Comment("How many ticks do the warm and cold panaceas for TFC Ambiental last for?")
        @Config.LangKey("config." + MODID + ".general.temperaturePanaceaDuration")
        public int temperaturePanaceaDuration = 30000;

        @Config.Comment("How many ticks does the health boost effect last for?")
        @Config.LangKey("config." + MODID + ".general.healthBoostDuration")
        public int healthBoostDuration = 30000;

        @Config.Comment("How many ticks does the keen sight effect last for?")
        @Config.LangKey("config." + MODID + ".general.keenSightDuration")
        public int keenSightDuration = 24000;

        @Config.Comment("How many ticks does the strong lungs effect last for?")
        @Config.LangKey("config." + MODID + ".general.strongLungsDuration")
        public int strongLungsDuration = 24000;

        @Config.Comment("How many ticks does the strong stomach effect last for?")
        @Config.LangKey("config." + MODID + ".general.strongStomachDuration")
        public int strongStomachDuration = 30000;

        @Config.Comment("How many ticks does the acuity effect last for?")
        @Config.LangKey("config." + MODID + ".general.acuityDuration")
        public int acuityDuration = 30000;

        @Config.Comment("If true, items are lost on death unless an acuity effect is present. (deleted not dropped)")
        @Config.LangKey("config." + MODID + ".general.enableItemLost")
        public boolean enableItemLoss = true;

        @Config.Comment("How far away should you respawn from your bed when without an acuity effect. Set to 0 to disable")
        @Config.LangKey("config." + MODID + ".general.randomSpawnRadius")
        public int randomSpawnRadius = 1000;

        @Config.Comment("If true, unsalted meat has a chance to give hunger unless a strong stomach effect is present.")
        @Config.LangKey("config." + MODID + ".general.enableParasites")
        public boolean enableParasites = true;

        @Config.Comment("If true, nutrients are lost on death.")
        @Config.LangKey("config." + MODID + ".general.enableNutrientsLost")
        public boolean enableNutrientsLost = true;
    }
}