package eyeq.santacostume;

import eyeq.util.client.model.UModelCreator;
import eyeq.util.client.model.UModelLoader;
import eyeq.util.client.model.gson.ItemmodelJsonFactory;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.resource.ULanguageCreator;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import eyeq.santacostume.item.ItemArmorSanta;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

import static eyeq.santacostume.SantaCostume.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
@Mod.EventBusSubscriber
public class SantaCostume {
    public static final String MOD_ID = "eyeq_santacostume";

    @Mod.Instance(MOD_ID)
    protected static SantaCostume instance;

    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static Item santaHelmet;
    public static Item santaChestPlate;
    public static Item santaLeggings;
    public static Item santaBoots;
    public static Item santaGloves;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        addRecipes();
        if(event.getSide().isServer()) {
            return;
        }
        renderItemModels();
        createFiles();
    }

    @SubscribeEvent
    protected static void registerItems(RegistryEvent.Register<Item> event) {
        santaHelmet = new ItemArmorSanta(0, EntityEquipmentSlot.HEAD).setUnlocalizedName("santaHelmet");
        santaChestPlate = new ItemArmorSanta(0, EntityEquipmentSlot.CHEST).setUnlocalizedName("santaChestPlate");
        santaLeggings = new ItemArmorSanta(0, EntityEquipmentSlot.LEGS).setUnlocalizedName("santaLeggings");
        santaBoots = new ItemArmorSanta(0, EntityEquipmentSlot.FEET).setUnlocalizedName("santaBoots");
        santaGloves = new Item() {
            @Override
            public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
                ItemStack itemStack = player.getHeldItem(hand);
                player.setHeldItem(hand, new ItemStack(Items.ENDER_PEARL));
                ActionResult result = Items.ENDER_PEARL.onItemRightClick(world, player, hand);
                player.setHeldItem(hand, itemStack);
                return new ActionResult<>(result.getType(), itemStack);
            }
        }.setMaxStackSize(1).setUnlocalizedName("santaGloves").setCreativeTab(CreativeTabs.TOOLS);

        GameRegistry.register(santaHelmet, resource.createResourceLocation("santa_helmet"));
        GameRegistry.register(santaChestPlate, resource.createResourceLocation("santa_chestplate"));
        GameRegistry.register(santaLeggings, resource.createResourceLocation("santa_leggings"));
        GameRegistry.register(santaBoots, resource.createResourceLocation("santa_boots"));
        GameRegistry.register(santaGloves, resource.createResourceLocation("santa_gloves"));
    }

    public static void addRecipes() {
        int w = EnumDyeColor.WHITE.getMetadata();
        int r = EnumDyeColor.RED.getMetadata();
        int b = EnumDyeColor.BLACK.getMetadata();
        GameRegistry.addRecipe(new ItemStack(santaHelmet), "RRW", "RRR", "W W",
                'W', new ItemStack(Blocks.WOOL, 1, w), 'R', new ItemStack(Blocks.WOOL, 1, r));
        GameRegistry.addRecipe(new ItemStack(santaChestPlate), "R R", "RWR", "WWW",
                'W', new ItemStack(Blocks.WOOL, 1, w), 'R', new ItemStack(Blocks.WOOL, 1, r));
        GameRegistry.addRecipe(new ItemStack(santaLeggings), "RRR", "R R", "R R",
                'R', new ItemStack(Blocks.WOOL, 1, r));
        GameRegistry.addRecipe(new ItemStack(santaBoots), "W W", "B B",
                'W', new ItemStack(Blocks.WOOL, 1, w), 'B', new ItemStack(Blocks.WOOL, 1, b));
        GameRegistry.addRecipe(new ItemStack(santaGloves), "R R",
                'R', new ItemStack(Blocks.WOOL, 1, r));
    }

    @SideOnly(Side.CLIENT)
    public static void renderItemModels() {
        UModelLoader.setCustomModelResourceLocation(santaHelmet);
        UModelLoader.setCustomModelResourceLocation(santaChestPlate);
        UModelLoader.setCustomModelResourceLocation(santaLeggings);
        UModelLoader.setCustomModelResourceLocation(santaBoots);
        UModelLoader.setCustomModelResourceLocation(santaGloves);
    }

    public static void createFiles() {
        File project = new File("../1.11.2-SantaCostume");

        LanguageResourceManager language = new LanguageResourceManager();

        language.register(LanguageResourceManager.EN_US, santaHelmet, "Santa Claus Hat");
        language.register(LanguageResourceManager.JA_JP, santaHelmet, "サンタ帽子");
        language.register(LanguageResourceManager.EN_US, santaChestPlate, "Santa Claus Jacket");
        language.register(LanguageResourceManager.JA_JP, santaChestPlate, "サンタ服");
        language.register(LanguageResourceManager.EN_US, santaLeggings, "Santa Claus Pants");
        language.register(LanguageResourceManager.JA_JP, santaLeggings, "サンタズボン");
        language.register(LanguageResourceManager.EN_US, santaBoots, "Santa Claus Boots");
        language.register(LanguageResourceManager.JA_JP, santaBoots, "サンタ靴");
        language.register(LanguageResourceManager.EN_US, santaGloves, "Santa Claus Gloves");
        language.register(LanguageResourceManager.JA_JP, santaGloves, "サンタ手袋");

        ULanguageCreator.createLanguage(project, MOD_ID, language);

        UModelCreator.createItemJson(project, santaHelmet, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, santaChestPlate, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, santaLeggings, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, santaBoots, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, santaGloves, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
    }
}
