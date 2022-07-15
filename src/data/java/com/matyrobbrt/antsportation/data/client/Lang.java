package com.matyrobbrt.antsportation.data.client;

import com.matyrobbrt.antsportation.Antsportation;
import com.matyrobbrt.antsportation.item.BoxItem;
import com.matyrobbrt.antsportation.registration.AntsportationEntities;
import com.matyrobbrt.antsportation.registration.AntsportationItems;
import com.matyrobbrt.antsportation.util.Translations;
import com.matyrobbrt.antsportation.util.Utils;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.data.LanguageProvider;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Locale;
import java.util.stream.Stream;

@ParametersAreNonnullByDefault
public class Lang extends LanguageProvider {
    public final AccessibleLanguageProvider enUd;

    public Lang(DataGenerator gen) {
        super(gen, Antsportation.MOD_ID, "en_us");
        enUd = new AccessibleLanguageProvider(gen, Antsportation.MOD_ID, "en_ud");
    }

    private static class AccessibleLanguageProvider extends LanguageProvider {

        public AccessibleLanguageProvider(DataGenerator gen, String modid, String locale) {
            super(gen, modid, locale);
        }

        @Override
        public void add(@NotNull String key, @NotNull String value) {
            super.add(key, value);
        }

        @Override
        protected void addTranslations() {}
    }

    @Override
    protected void addTranslations() {
        AntsportationItems.ITEMS.getEntries().forEach(item -> {
            final var name = String.join(" ", Stream.of(item.getId().getPath().split("_")).map(Lang::capitalize).toList());
            addItem(item, name);
        });

        for (final var value : Translations.values()) {
            add(value.key, value.englishTranslation);
        }

        for (final var tier : BoxItem.BoxTier.values()) {
            add(tier.getTranslationKey(), capitalize(tier.name().toLowerCase(Locale.ROOT)));
        }

        AntsportationEntities.ENTITIES.getEntries().forEach(entity -> {
            final var name = String.join(" ", Stream.of(entity.getId().getPath().split("_")).map(Lang::capitalize).toList());
            add(entity.get(), name);
        });

        add(((TranslatableComponent) Antsportation.TAB.getDisplayName()).getKey(), "Antsportation");
    }

    private static String capitalize(String str) {
        final var firstChar = str.charAt(0);
        return String.valueOf(firstChar).toUpperCase(Locale.ROOT) + str.substring(1);
    }

    public void add(String key, String value) {
        super.add(key, value);
        enUd.add(key, Utils.toUpsideDown(value));
    }

}
