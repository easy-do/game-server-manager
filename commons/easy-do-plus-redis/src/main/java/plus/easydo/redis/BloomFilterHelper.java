package plus.easydo.redis;

import com.google.common.base.Preconditions;
import com.google.common.hash.Funnel;
import com.google.common.hash.Hashing;

/**
 * 布隆过滤器
 * @author laoyu
 * @version 1.0
 * @date 2022/2/20
 */
public class BloomFilterHelper<T> {

    private int numHashFunctions;

    private int bitSize;

    private Funnel<T> funnel;

    public BloomFilterHelper(Funnel<T> funnel, int expectedInsertions, double fpp) {
        Preconditions.checkArgument(funnel != null, "funnel不能为空");
        this.funnel = funnel;
        this.bitSize = this.optimalNumOfBits((long)expectedInsertions, fpp);
        this.numHashFunctions = this.optimalNumOfHashFunctions((long)expectedInsertions, (long)this.bitSize);
    }

    public int[] murmurHashOffset(T value) {
        int[] offset = new int[this.numHashFunctions];
        long hash64 = Hashing.murmur3_128().hashObject(value, this.funnel).asLong();
        int hash1 = (int)hash64;
        int hash2 = (int)(hash64 >>> 32);

        for(int i = 1; i <= this.numHashFunctions; ++i) {
            int nextHash = hash1 + i * hash2;
            if (nextHash < 0) {
                nextHash = ~nextHash;
            }

            offset[i - 1] = nextHash % this.bitSize;
        }

        return offset;
    }

    private int optimalNumOfBits(long n, double p) {
        if ((p - 0.0d) == 0) {
            p = 4.9E-324D;
        }

        return (int)((double)(-n) * Math.log(p) / (Math.log(2.0D) * Math.log(2.0D)));
    }

    private int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int)Math.round((double)m / (double)n * Math.log(2.0D)));
    }
}
