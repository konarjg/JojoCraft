package com.github.konarjg.jojocraft.block;

import com.github.konarjg.jojocraft.JojoCraft;
import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.event.PowerHandler;
import com.github.konarjg.jojocraft.power.Power;
import com.github.konarjg.jojocraft.power.PowerType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCoffin extends Block {
    public enum EnumPart implements IStringSerializable {
        HEAD,
        FOOT;

        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }

    public static final PropertyEnum<EnumPart> PART = PropertyEnum.create("part", EnumPart.class);
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    protected static final AxisAlignedBB COFFIN_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.6875D, 1.0D);

    public BlockCoffin() {
        super(Material.WOOD);
        setRegistryName(new ResourceLocation(Tags.MOD_ID, "coffin"));
        setTranslationKey("coffin");
        setCreativeTab(JojoCraft.creativeTab);
        setHarvestLevel("axe", 0);
        setHardness(1.5f);
        setDefaultState(this.blockState.getBaseState().withProperty(PART, EnumPart.FOOT).withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        EnumPart part = state.getValue(PART);
        EnumFacing facing = state.getValue(FACING);

        BlockPos otherPartPos = pos.offset(facing);
        world.setBlockToAir(pos);

        if (world.getBlockState(otherPartPos).getBlock() == this) {
            world.setBlockToAir(otherPartPos);
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        EnumFacing facing = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 10, false).getHorizontalFacing();
        BlockPos frontPos = pos.offset(facing);

        return world.getBlockState(pos).getBlock().isReplaceable(world, pos) &&
                world.getBlockState(frontPos).getBlock().isReplaceable(world, frontPos);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        EnumFacing facing = placer.getHorizontalFacing();
        BlockPos frontPos = pos.offset(facing);

        world.setBlockState(pos, state.withProperty(PART, EnumPart.FOOT).withProperty(FACING, facing), 2);
        world.setBlockState(frontPos, state.withProperty(PART, EnumPart.HEAD).withProperty(FACING, facing.getOpposite()), 2);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, PART, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.byHorizontalIndex(meta & 3);
        EnumPart part = (meta & 4) == 0 ? EnumPart.FOOT : EnumPart.HEAD;
        return this.getDefaultState().withProperty(PART, part).withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = state.getValue(FACING).getHorizontalIndex();
        if (state.getValue(PART) == EnumPart.HEAD) {
            meta |= 4;
        }
        return meta;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        }

        if (playerIn.isSneaking()) {
            return false;
        }

        Power power = playerIn.getCapability(PowerHandler.CAPABILITY_POWER, null);

        if (power.getType() != PowerType.VAMPIRE) {
            playerIn.sendMessage(new TextComponentString("Only vampires can sleep in coffins!"));
            return false;
        }

        if (!worldIn.isDaytime()) {
            playerIn.sendMessage(new TextComponentString("You can only sleep during the day!"));
            return false;
        }

        worldIn.setWorldTime(13000);
        playerIn.sendMessage(new TextComponentString("The night has fallen, it's time to feed!"));

        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumPushReaction getPushReaction(IBlockState state)
    {
        return EnumPushReaction.DESTROY;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return COFFIN_AABB;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }
}
