package net.mikov.dinos.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {

    public static final FoodComponent RAW_PRIMAL_MEAT = new FoodComponent.Builder().hunger(4).saturationModifier(0.25f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 200), 0.2f).build();
    public static final FoodComponent COOKED_PRIMAL_MEAT = new FoodComponent.Builder().hunger(8).saturationModifier(0.35f)
            .statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 400), 1f).build();
    public static final FoodComponent RAW_COEL = new FoodComponent.Builder().hunger(2).saturationModifier(0.25f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 200), 0.2f).build();
    public static final FoodComponent COOKED_COEL = new FoodComponent.Builder().hunger(5).saturationModifier(0.35f)
            .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 400), 0.65f).build();
    public static final FoodComponent RAW_PIRANHA = new FoodComponent.Builder().hunger(4).saturationModifier(0.25f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 200), 0.2f).build();
    public static final FoodComponent COOKED_PIRANHA = new FoodComponent.Builder().hunger(6).saturationModifier(0.35f)
            .statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 500), 0.65f).build();

}
