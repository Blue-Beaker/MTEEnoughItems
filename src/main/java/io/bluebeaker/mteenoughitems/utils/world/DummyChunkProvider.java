package io.bluebeaker.mteenoughitems.utils.world;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyChunkProvider implements IChunkProvider {
    private final DummyWorld world;
    public final Map<Long, Chunk> chunks = new HashMap<>();

    public DummyChunkProvider(DummyWorld world){
        this.world=world;
    }

    @Nullable
    @Override
    public Chunk getLoadedChunk(int x, int z) {
        return chunks.get(ChunkPos.asLong(x,z));
    }

    @Override
    public Chunk provideChunk(int x, int z) {
        long chunkPos = ChunkPos.asLong(x,z);
        Chunk chunk = chunks.get(chunkPos);
        if (chunk==null) {
            chunk = new DummyChunk(world, x, z);
            chunks.put(chunkPos, chunk);
        }
        return chunk;
    }

    @Override
    public boolean tick() {
        return false;
    }

    public List<TileEntity> getTileEntities(){
        List<TileEntity> tiles = new ArrayList<>();
        for (Chunk chunk : chunks.values()) {
            chunk.onTick(false);
            tiles.addAll(chunk.getTileEntityMap().values());
        }
        return tiles;
    }

    @Override
    public String makeString() {
        return "String";
    }

    @Override
    public boolean isChunkGeneratedAt(int x, int z) {
        return chunks.containsKey(ChunkPos.asLong(x, z));
    }

    public void clear() {
        chunks.clear();
    }
}
