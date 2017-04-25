package eyeq.santacostume.item;

import eyeq.santacostume.SantaCostume;
import eyeq.util.UItemArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorSanta extends UItemArmor {
    public static final ArmorMaterial armorMaterialSanta = EnumHelper.addArmorMaterial("santa", "", 1, new int[]{2, 5, 4, 1}, 30, null, 0.0F);

    private static final ResourceLocation armorName = new ResourceLocation(SantaCostume.MOD_ID, "santa");

    public ItemArmorSanta(int renderIndex, EntityEquipmentSlot slot) {
        super(armorMaterialSanta, renderIndex, slot, armorName);
        this.setNoRepair();
    }

    @Override
    public String getArmorTexture(ItemStack itemStack, Entity entity, EntityEquipmentSlot slot, String type) {
        if(type == null) {
            return super.getArmorTexture(itemStack, entity, slot, type);
        }
        return null;
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    @Override
    protected void onArmorTickHead(World world, EntityPlayer player, ItemStack itemStack) {
        for(Object obj : world.getEntitiesWithinAABB(EntityCreature.class, player.getEntityBoundingBox().expand(8.0D, 4.0D, 8.0D))) {
            EntityCreature entity = (EntityCreature) obj;
            if(entity.isChild()) {
                entity.setAttackTarget(null);
                entity.getNavigator().tryMoveToEntityLiving(player, entity.getAIMoveSpeed());
            }
        }
    }

    @Override
    protected void onArmorTickChest(World world, EntityPlayer player, ItemStack itemStack) {
        player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 1, 16));
    }

    @Override
    protected void onArmorTickLegs(World world, EntityPlayer player, ItemStack itemStack) {
        player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1, 16));
        player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 1, 2));
    }

    @Override
    protected void onArmorTickFeet(World world, EntityPlayer player, ItemStack itemStack) {
        if(!player.onGround) {
            player.fallDistance = 1.0F;
        }
    }
}
