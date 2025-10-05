package io.bluebeaker.mteenoughitems.jei.buildcraft;

import buildcraft.silicon.gate.GateVariant;
import buildcraft.silicon.item.ItemPluggableFacade;
import buildcraft.silicon.item.ItemPluggableGate;
import buildcraft.silicon.plug.FacadeInstance;
import buildcraft.silicon.plug.FacadePhasedState;
import buildcraft.silicon.plug.FacadeStateManager;
import buildcraft.silicon.recipe.FacadeAssemblyRecipes;
import io.bluebeaker.mteenoughitems.utils.ItemUtils;
import mezz.jei.api.ISubtypeRegistry;
import net.minecraft.item.ItemStack;

public class FacadeSubTypeInterpreter implements ISubtypeRegistry.ISubtypeInterpreter {
    @Override
    public String apply(ItemStack itemStack) {
        FacadeInstance state = ItemPluggableFacade.getStates(itemStack);
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (FacadePhasedState phasedState : state.phasedStates) {
            if(i>0) builder.append(",");
            builder.append(phasedState.activeColour).append(":").append(ItemUtils.getStackRepresentation(phasedState.stateInfo.requiredStack));
            i++;
        }

        return builder.toString();
    }
}
