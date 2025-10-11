package io.bluebeaker.mteenoughitems.utils;

import net.minecraftforge.common.DimensionManager;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringUtils {
    public static String getDimensionName(int dim){
        if(!DimensionManager.isDimensionRegistered(dim)) return String.valueOf(dim);
        return DimensionManager.getProviderType(dim).getName();
    }
    public static String join(Stream<?> stream, CharSequence delimiter){
        return stream.map(String::valueOf).collect(Collectors.joining(delimiter));
    }
}
