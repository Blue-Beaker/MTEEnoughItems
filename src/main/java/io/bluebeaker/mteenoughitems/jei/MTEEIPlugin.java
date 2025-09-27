package io.bluebeaker.mteenoughitems.jei;

import forestry.energy.ModuleEnergy;
import forestry.energy.gui.GuiEngineBiogas;
import forestry.energy.gui.GuiGenerator;
import forestry.plugins.PluginIC2;
import io.bluebeaker.mteenoughitems.MTEEnoughItems;
import io.bluebeaker.mteenoughitems.MTEEnoughItemsConfig;
import io.bluebeaker.mteenoughitems.jei.forestry.BioGeneratorCategory;
import io.bluebeaker.mteenoughitems.jei.forestry.BiogasEngineCategory;
import io.bluebeaker.mteenoughitems.jei.railcraft.FluidFireboxCategory;
import io.bluebeaker.mteenoughitems.utils.ModChecker;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mods.railcraft.client.gui.GuiBoilerFluid;
import mods.railcraft.common.blocks.RailcraftBlocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;


@JEIPlugin
public class MTEEIPlugin implements IModPlugin {

  private static IJeiRuntime jeiRuntime = null;
  public static IModRegistry modRegistry;

  @Override
  public void registerCategories(IRecipeCategoryRegistration registry) {
    IJeiHelpers jeiHelpers = registry.getJeiHelpers();
    IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
    if(ModChecker.Forestry.isLoaded()){
      if(MTEEnoughItemsConfig.forestry.biogas_engine) {
        registry.addRecipeCategories(new BiogasEngineCategory(jeiHelpers.getGuiHelper()));
      }
      if(MTEEnoughItemsConfig.forestry.bio_generator && ModChecker.IC2.isLoaded()) {
        registry.addRecipeCategories(new BioGeneratorCategory(jeiHelpers.getGuiHelper()));
      }
    }
    if(ModChecker.Railcraft.isLoaded()){
      if(MTEEnoughItemsConfig.railcraft.fluid_firebox) {
        registry.addRecipeCategories(new FluidFireboxCategory(jeiHelpers.getGuiHelper()));
      }
    }
  }

  @Override
  public void register(IModRegistry registry) {
    modRegistry=registry;
    IJeiHelpers jeiHelpers = registry.getJeiHelpers();

    MTEEnoughItems.getLogger().info("Started loading recipes...");

    if(ModChecker.Forestry.isLoaded()){
      if(MTEEnoughItemsConfig.forestry.biogas_engine){
        registry.addRecipes(BiogasEngineCategory.getRecipes(jeiHelpers),BiogasEngineCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModuleEnergy.getBlocks().biogasEngine),BiogasEngineCategory.UID);
        registry.addRecipeClickArea(GuiEngineBiogas.class,52,27,36,14,BiogasEngineCategory.UID);
      }

      if(MTEEnoughItemsConfig.forestry.bio_generator && ModChecker.IC2.isLoaded()){
        registry.addRecipes(BioGeneratorCategory.getRecipes(jeiHelpers),BioGeneratorCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(PluginIC2.getBlocks().generator),BioGeneratorCategory.UID);
        registry.addRecipeClickArea(GuiGenerator.class,68,38,40,18,BioGeneratorCategory.UID);
      }
    }
    if(ModChecker.Railcraft.isLoaded()){
      if(MTEEnoughItemsConfig.railcraft.fluid_firebox){
        registry.addRecipes(FluidFireboxCategory.getRecipes(jeiHelpers), FluidFireboxCategory.UID);
        ItemBlock firebox = RailcraftBlocks.BOILER_FIREBOX_FLUID.item();
        registry.addRecipeClickArea(GuiBoilerFluid.class,62,38,14,14,FluidFireboxCategory.UID);
        if(firebox!=null)
          registry.addRecipeCatalyst(new ItemStack(firebox), FluidFireboxCategory.UID);
      }
    }

    MTEEnoughItems.getLogger().info("Loaded all recipes!");
  }

  @Override
  public void onRuntimeAvailable(IJeiRuntime jeiRuntimeIn) {
    MTEEIPlugin.jeiRuntime = jeiRuntimeIn;
  }

  public static void setFilterText(@Nonnull String filterText) {
    jeiRuntime.getIngredientFilter().setFilterText(filterText);
  }

  public static String getFilterText() {
    return jeiRuntime.getIngredientFilter().getFilterText();
  }

  public static void showCraftingRecipes() {
  }

  @Override
  public void registerSubtypes(ISubtypeRegistry subtypeRegistry) {
    IModPlugin.super.registerSubtypes(subtypeRegistry);
  }

  @Override
  public void registerIngredients(IModIngredientRegistration ingredientRegistration) {
  }

}