package mods.bwf.management;

import com.github.bsideup.jabel.Desugar;

@Desugar
public record AddonSoundRegistryEntry(String sound, int variantCount)  {

    public AddonSoundRegistryEntry(String sound) {
        this(sound, 1);
    }

    public AddonSoundRegistryEntry(String sound, int variantCount) {
        if (variantCount < 1) {
            throw new IllegalArgumentException("Variant count cannot be less than one!");
        }


        this.sound = sound;
        this.variantCount = variantCount;
        BTWSoundManager.addSoundToRegistry(this);
    }
}
