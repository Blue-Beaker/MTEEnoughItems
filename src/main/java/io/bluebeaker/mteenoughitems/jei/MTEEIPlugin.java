package io.bluebeaker.mteenoughitems.jei;

import forestry.energy.ModuleEnergy;
import forestry.energy.gui.GuiEngineBiogas;
import forestry.energy.gui.GuiEnginePeat;
import forestry.energy.gui.GuiGenerator;
import forestry.plugins.PluginIC2;
import io.bluebeaker.mteenoughitems.MTEEnoughItems;
import io.bluebeaker.mteenoughitems.MTEEnoughItemsConfig;
import io.bluebeaker.mteenoughitems.jei.forestry.BioGeneratorCategory;
import io.bluebeaker.mteenoughitems.jei.forestry.BiogasEngineCategory;
import io.bluebeaker.mteenoughitems.jei.forestry.PeatEngineCategory;
import io.bluebeaker.mteenoughitems.jei.railcraft.BlastFurnaceFuelCategory;
import io.bluebeaker.mteenoughitems.jei.railcraft.BoilerCategory;
import io.bluebeaker.mteenoughitems.jei.railcraft.FluidFireboxCategory;
import io.bluebeaker.mteenoughitems.utils.ModChecker;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mods.railcraft.client.gui.GuiBlastFurnace;
import mods.railcraft.client.gui.GuiBoilerFluid;
import mods.railcraft.client.gui.GuiBoilerSolid;
import mods.railcraft.common.blocks.RailcraftBlocks;
import mods.railcraft.common.carts.RailcraftCarts;
import mods.railcraft.common.items.RailcraftItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


@JEIPlugin
public class MTEEIPlugin implements IModPlugin {

  private static IJeiRuntime jeiRuntime = null;
  public static IModRegistry modRegistry;

  @Override
  public void registerCategories(IRecipeCategoryRegistration registry) {
    IJeiHelpers jeiHelpers = registry.getJeiHelpers();
    IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
    if(ModChecker.Forestry.isLoaded()){
      if(MTEEnoughItemsConfig.forestry.peat_engine) {
        registry.addRecipeCategories(new PeatEngineCategory(jeiHelpers.getGuiHelper()));
      }
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
      if(MTEEnoughItemsConfig.railcraft.boiler) {
        registry.addRecipeCategories(new BoilerCategory(jeiHelpers.getGuiHelper()));
      }
      if(MTEEnoughItemsConfig.railcraft.blast_furnace_fuel) {
        registry.addRecipeCategories(new BlastFurnaceFuelCategory(jeiHelpers.getGuiHelper()));
      }
    }
  }

  @Override
  public void register(IModRegistry registry) {
    modRegistry=registry;
    IJeiHelpers jeiHelpers = registry.getJeiHelpers();

    MTEEnoughItems.getLogger().info("Started loading recipes...");

    if(ModChecker.Forestry.isLoaded()){
      if(MTEEnoughItemsConfig.forestry.peat_engine){
        registry.addRecipes(PeatEngineCategory.getRecipes(jeiHelpers),PeatEngineCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModuleEnergy.getBlocks().peatEngine),PeatEngineCategory.UID);
      }
      if(MTEEnoughItemsConfig.forestry.biogas_engine){
        registry.addRecipes(BiogasEngineCategory.getRecipes(jeiHelpers),BiogasEngineCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModuleEnergy.getBlocks().biogasEngine),BiogasEngineCategory.UID);
      }

      if(MTEEnoughItemsConfig.forestry.bio_generator && ModChecker.IC2.isLoaded()){
        registry.addRecipes(BioGeneratorCategory.getRecipes(jeiHelpers),BioGeneratorCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(PluginIC2.getBlocks().generator),BioGeneratorCategory.UID);
      }

      registry.addRecipeClickArea(GuiEngineBiogas.class,52,27,36,14,BiogasEngineCategory.UID);
      registry.addRecipeClickArea(GuiGenerator.class,68,38,40,18,BioGeneratorCategory.UID);
      registry.addRecipeClickArea(GuiEnginePeat.class,45,27,14,14, PeatEngineCategory.UID);
    }
    if(ModChecker.Railcraft.isLoaded()){
      if(MTEEnoughItemsConfig.railcraft.fluid_firebox){
        registry.addRecipes(FluidFireboxCategory.getRecipes(jeiHelpers), FluidFireboxCategory.UID);
      }
      if(MTEEnoughItemsConfig.railcraft.boiler) {
        registry.addRecipes(BoilerCategory.getRecipes(jeiHelpers), BoilerCategory.UID);
      }
      if(MTEEnoughItemsConfig.railcraft.blast_furnace_fuel) {
        registry.addRecipes(BlastFurnaceFuelCategory.getRecipes(jeiHelpers), BlastFurnaceFuelCategory.UID);
      }

      registry.addRecipeClickArea(GuiBlastFurnace.class,56,36,14,14,BlastFurnaceFuelCategory.UID);

      registry.addRecipeClickArea(GuiBoilerFluid.class,62,38,14,14,FluidFireboxCategory.UID,BoilerCategory.UID);
      registry.addRecipeClickArea(GuiBoilerSolid.class,62,22,14,14,BoilerCategory.UID);

      addItemCatalystIfNotNull(RailcraftBlocks.BLAST_FURNACE.item(), registry, BlastFurnaceFuelCategory.UID);

      addItemCatalystIfNotNull(RailcraftBlocks.BOILER_FIREBOX_SOLID.item(), registry, BoilerCategory.UID);
      addItemCatalystIfNotNull(RailcraftCarts.LOCO_STEAM_SOLID.getItem(), registry, BoilerCategory.UID);
      addItemCatalystIfNotNull(RailcraftBlocks.BOILER_FIREBOX_FLUID.item(), registry, FluidFireboxCategory.UID,BoilerCategory.UID);
      addItemCatalystIfNotNull(RailcraftBlocks.BOILER_TANK_PRESSURE_HIGH.item(), registry, FluidFireboxCategory.UID,BoilerCategory.UID);
      addItemCatalystIfNotNull(RailcraftBlocks.BOILER_TANK_PRESSURE_LOW.item(), registry, FluidFireboxCategory.UID,BoilerCategory.UID);
    }

    MTEEnoughItems.getLogger().info("Loaded all recipes!");
  }

  private static void addItemCatalystIfNotNull(@Nullable Item catalyst, IModRegistry registry, String... uid) {
    if (catalyst != null)
      registry.addRecipeCatalyst(new ItemStack(catalyst), uid);
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