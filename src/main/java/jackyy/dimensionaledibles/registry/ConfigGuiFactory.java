package jackyy.dimensionaledibles.registry;

import jackyy.dimensionaledibles.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;

import java.util.ArrayList;
import java.util.Set;

public class ConfigGuiFactory implements IModGuiFactory {

    @Override
    public void initialize(Minecraft minecraftInstance) {
    }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return ConfigGui.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Override @SuppressWarnings("deprecation")
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }

    public static class ConfigGui extends GuiConfig {
        private static Configuration config = ModConfig.ConfigHolder.getConfig();
        public ConfigGui(GuiScreen parentScreen) {
            super(parentScreen, new ArrayList<>(new ConfigElement(config.getCategory("general")).getChildElements()),
                    Reference.MODID, false, false, GuiConfig.getAbridgedConfigPath(config.toString()));
        }
        @Override
        public void onGuiClosed() {
            super.onGuiClosed();
            config.save();
        }
    }

}
