package io.bluebeaker.mteenoughitems.jei;

import forestry.Forestry;
import forestry.energy.ModuleEnergy;
import io.bluebeaker.mteenoughitems.MTEEnoughItems;
import io.bluebeaker.mteenoughitems.MTEEnoughItemsConfig;
import io.bluebeaker.mteenoughitems.jei.generator.BiogasEngineCategory;
import io.bluebeaker.mteenoughitems.utils.ModChecker;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
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