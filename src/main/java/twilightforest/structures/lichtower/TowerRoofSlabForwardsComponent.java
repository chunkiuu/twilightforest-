package twilightforest.structures.lichtower;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;

import java.util.Random;

/**
 * A flat tower roof using slabs that is larger than the tower under it.
 *
 * @author Ben
 */
public class TowerRoofSlabForwardsComponent extends TowerRoofSlabComponent {

	public TowerRoofSlabForwardsComponent(ServerLevel level, CompoundTag nbt) {
		super(LichTowerPieces.TFLTRSF, nbt);
	}

	public TowerRoofSlabForwardsComponent(TFFeature feature, int i, TowerWingComponent wing, int x, int y, int z) {
		super(LichTowerPieces.TFLTRSF, feature, i, wing, x, y, z);

		// same alignment
		this.setOrientation(wing.getOrientation());
		// the overhang roof is like a cap roof that's 2 sizes bigger
		this.size = wing.size + 2; // assuming only square towers and roofs right now.
		this.height = size / 2;

		// bounding box
		makeAttachedOverhangBB(wing);
	}

	/**
	 * Makes flat hip roof
	 */
	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		BlockState birchSlab = Blocks.BIRCH_SLAB.defaultBlockState();
		BlockState birchDoubleSlab = Blocks.BIRCH_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.DOUBLE);

		for (int y = 0; y <= height; y++) {
			int min = 2 * y;
			int max = size - (2 * y) - 1;
			for (int x = 0; x <= max - 1; x++) {
				for (int z = min; z <= max; z++) {
					if (x == max - 1 || z == min || z == max) {
						placeBlock(world, birchSlab, x, y, z, sbb);
					} else {
						placeBlock(world, birchDoubleSlab, x, y, z, sbb);
					}
				}
			}
		}
		return true;
	}
}
