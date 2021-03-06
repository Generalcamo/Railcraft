/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016
 http://railcraft.info

 This code is the property of CovertJaguar
 and may only be used with explicit written
 permission unless otherwise specified on the
 license page at http://railcraft.info/wiki/info:license.
 -----------------------------------------------------------------------------*/
package mods.railcraft.common.modules;

import mods.railcraft.api.core.RailcraftModule;
import mods.railcraft.api.crafting.RailcraftCraftingManager;
import mods.railcraft.common.blocks.RailcraftBlocks;
import mods.railcraft.common.blocks.detector.EnumDetector;
import mods.railcraft.common.blocks.machine.alpha.EnumMachineAlpha;
import mods.railcraft.common.blocks.machine.beta.EnumMachineBeta;
import mods.railcraft.common.blocks.machine.manipulator.ManipulatorVariant;
import mods.railcraft.common.carts.RailcraftCarts;
import mods.railcraft.common.items.Metal;
import mods.railcraft.common.items.RailcraftItems;
import mods.railcraft.common.plugins.color.EnumColor;
import mods.railcraft.common.plugins.forge.CraftingPlugin;
import mods.railcraft.common.util.crafting.CartFilterRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * @author CovertJaguar <http://www.railcraft.info>
 */
@RailcraftModule(value = "railcraft:transport", description = "loaders, cargo cart, tank cart, multiblock tanks")
public class ModuleTransport extends RailcraftModulePayload {
    public ModuleTransport() {
        setEnabledEventHandler(new ModuleEventHandler() {
            @Override
            public void construction() {
                add(
                        RailcraftCarts.CARGO,
                        RailcraftCarts.TANK,
//                        RailcraftBlocks.machine_alpha,
//                        RailcraftBlocks.machine_beta,
                        RailcraftBlocks.MACHINE_MANIPULATOR
                );
            }

            @Override
            public void preInit() {
                EnumMachineAlpha alpha = EnumMachineAlpha.TANK_WATER;
                if (alpha.isAvailable())
                    CraftingPlugin.addRecipe(alpha.getItem(6),
                            "WWW",
                            "ISI",
                            "WWW",
                            'I', "ingotIron",
                            'S', "slimeball",
                            'W', "plankWood");

                initIronTank();
                initSteelTank();

                EnumMachineBeta voidChest = EnumMachineBeta.VOID_CHEST;
                if (voidChest.isAvailable())
                    CraftingPlugin.addRecipe(voidChest.getItem(),
                            "OOO",
                            "OPO",
                            "OOO",
                            'O', new ItemStack(Blocks.OBSIDIAN),
                            'P', new ItemStack(Items.ENDER_PEARL));

                ManipulatorVariant itemLoader = ManipulatorVariant.ITEM_LOADER;
                if (itemLoader.isAvailable()) {
                    ItemStack stack = itemLoader.getItem();
                    ItemStack detector = EnumDetector.ITEM.getItem();
                    if (detector == null)
                        detector = new ItemStack(Blocks.STONE_PRESSURE_PLATE);
                    CraftingPlugin.addRecipe(stack,
                            "SSS",
                            "SLS",
                            "SDS",
                            'S', "cobblestone",
                            'D', detector,
                            'L', new ItemStack(Blocks.HOPPER));

                    itemLoader = ManipulatorVariant.ITEM_LOADER_ADVANCED;
                    if (itemLoader.isAvailable())
                        CraftingPlugin.addRecipe(itemLoader.getItem(),
                                "IRI",
                                "RLR",
                                "ISI",
                                'I', "ingotSteel",
                                'R', "dustRedstone",
                                'S', RailcraftItems.SHOVEL_STEEL,
                                'L', stack);
                }

                ManipulatorVariant itemUnloader = ManipulatorVariant.ITEM_UNLOADER;
                if (itemUnloader.isAvailable()) {
                    ItemStack stack = itemUnloader.getItem();
                    ItemStack detector = EnumDetector.ITEM.getItem();
                    if (detector == null)
                        detector = new ItemStack(Blocks.STONE_PRESSURE_PLATE);
                    CraftingPlugin.addRecipe(stack,
                            "SSS",
                            "SDS",
                            "SLS",
                            'S', "cobblestone",
                            'D', detector,
                            'L', new ItemStack(Blocks.HOPPER));

                    itemUnloader = ManipulatorVariant.ITEM_UNLOADER_ADVANCED;
                    if (itemUnloader.isAvailable())
                        CraftingPlugin.addRecipe(itemUnloader.getItem(),
                                "IRI",
                                "RLR",
                                "ISI",
                                'I', "ingotSteel",
                                'R', "dustRedstone",
                                'S', RailcraftItems.SHOVEL_STEEL,
                                'L', stack);
                }

                ManipulatorVariant liquidLoader = ManipulatorVariant.FLUID_LOADER;

                if (liquidLoader.isAvailable()) {
                    ItemStack detector = EnumDetector.TANK.getItem();
                    if (detector == null)
                        detector = new ItemStack(Blocks.STONE_PRESSURE_PLATE);
                    CraftingPlugin.addRecipe(liquidLoader.getItem(),
                            "GLG",
                            "G G",
                            "GDG",
                            'D', detector,
                            'G', "blockGlassColorless",
                            'L', Blocks.HOPPER);
                }

                ManipulatorVariant liquidUnloader = ManipulatorVariant.FLUID_UNLOADER;
                if (liquidUnloader.isAvailable()) {
                    ItemStack detector = EnumDetector.TANK.getItem();
                    if (detector == null)
                        detector = new ItemStack(Blocks.STONE_PRESSURE_PLATE);
                    CraftingPlugin.addRecipe(liquidUnloader.getItem(),
                            "GDG",
                            "G G",
                            "GLG",
                            'D', detector,
                            'G', "blockGlassColorless",
                            'L', Blocks.HOPPER);
                }

//        EnumMachineDelta delta = EnumMachineDelta.CAGE;
//        if (delta.register())
//            CraftingPlugin.addShapedOreRecipe(alpha.getItem(6), 
//                "III",
//                "IWI",
//                "PPP",
//                'I', new ItemStack(Block.fenceIron),
//                'W', new ItemStack(Item.wheat),
//                'P', ItemPlate.getPlate(ItemPlate.EnumPlate.STEEL));
                RailcraftCarts cart = RailcraftCarts.TANK;

                if (cart.isLoaded()) {
                    if (EnumMachineBeta.TANK_IRON_GAUGE.isAvailable()) {
                        CraftingPlugin.addRecipe(cart.getStack(),
                                "T",
                                "M",
                                'T', EnumMachineBeta.TANK_IRON_GAUGE.getItem(),
                                'M', Items.MINECART);
                    } else {
                        CraftingPlugin.addRecipe(cart.getStack(),
                                "GGG",
                                "GMG",
                                "GGG",
                                'G', "blockGlassColorless",
                                'M', Items.MINECART);
                    }
                    CraftingPlugin.addRecipe(new CartFilterRecipe());
                }
            }

            private void addColorRecipes(EnumMachineBeta type) {
                for (EnumColor color : EnumColor.VALUES) {
                    ItemStack output = getColorTank(type, color, 8);
                    CraftingPlugin.addRecipe(output,
                            "OOO",
                            "ODO",
                            "OOO",
                            'O', type.getItem(),
                            'D', color.getDyeOreDictTag());
                }
            }

            private ItemStack getColorTank(EnumMachineBeta type, EnumColor color, int qty) {
                ItemStack stack = type.getItem(qty);
                color.setItemColor(stack);
                return stack;
            }

            private boolean defineTank(EnumMachineBeta type, Object... recipe) {
                if (type.isAvailable()) {
                    addColorRecipes(type);
                    CraftingPlugin.addRecipe(getColorTank(type, EnumColor.WHITE, 8), recipe);
                    return true;
                }
                return false;
            }

            private boolean defineIronTank(EnumMachineBeta type, Object... recipe) {
                if (defineTank(type, recipe)) {
                    RailcraftCraftingManager.blastFurnace.addRecipe(type.getItem(), true, false, 640, RailcraftItems.NUGGET.getStack(4, Metal.STEEL));
                    return true;
                }
                return false;
            }

            private void initIronTank() {
                defineIronTank(EnumMachineBeta.TANK_IRON_WALL,
                        "PP",
                        "PP",
                        'P', RailcraftItems.PLATE, Metal.IRON);

                defineIronTank(EnumMachineBeta.TANK_IRON_GAUGE,
                        "GPG",
                        "PGP",
                        "GPG",
                        'P', RailcraftItems.PLATE.getRecipeObject(Metal.IRON),
                        'G', "paneGlassColorless");

                defineIronTank(EnumMachineBeta.TANK_IRON_VALVE,
                        "GPG",
                        "PLP",
                        "GPG",
                        'P', RailcraftItems.PLATE.getRecipeObject(Metal.IRON),
                        'L', new ItemStack(Blocks.LEVER),
                        'G', new ItemStack(Blocks.IRON_BARS));
            }

            private void initSteelTank() {
                defineTank(EnumMachineBeta.TANK_STEEL_WALL,
                        "PP",
                        "PP",
                        'P', RailcraftItems.PLATE.getRecipeObject(Metal.STEEL));

                defineTank(EnumMachineBeta.TANK_STEEL_GAUGE,
                        "GPG",
                        "PGP",
                        "GPG",
                        'P', RailcraftItems.PLATE.getRecipeObject(Metal.STEEL),
                        'G', "paneGlassColorless");

                defineTank(EnumMachineBeta.TANK_STEEL_VALVE,
                        "GPG",
                        "PLP",
                        "GPG",
                        'P', RailcraftItems.PLATE.getRecipeObject(Metal.STEEL),
                        'L', new ItemStack(Blocks.LEVER),
                        'G', new ItemStack(Blocks.IRON_BARS));
            }
        });
    }
}
