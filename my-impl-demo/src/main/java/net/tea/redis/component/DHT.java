package net.tea.redis.component;

import net.tea.redis.algorithm.Fnv1_32Hash;
import net.tea.redis.algorithm.Hash;
import net.tea.redis.algorithm.MurmurHash;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author teaho2015<at>gmail<dot>com
 * @since 2018-09
 */
public class DHT<T> {

    private TreeMap<Long, T> virtualNodes;

    private List<T> nodes;

    private int virtualNodeNum = 2 << 7;

    private Hash nodeHashing;

    private Hash keyHashing;

    public DHT(List<T> nodes) {
        this(nodes, 2 << 7, new MurmurHash());
    }

    public DHT(List<T> nodes, int virtualNodeNum, Hash nodeHashing) {
        this(nodes, 2 << 7, nodeHashing, new Fnv1_32Hash());
    }

    public DHT(List<T> nodes, int virtualNodeNum, Hash nodeHashing, Hash keyHashing) {
        this.nodes = nodes;
        this.virtualNodeNum = virtualNodeNum;
        this.nodeHashing = nodeHashing;
        this.keyHashing = keyHashing;

        TreeMap<Long, T> t = new TreeMap<>();
        for (int i = 0; i < nodes.size(); i++) {
            final T node = nodes.get(i);
            for (int j = 0; j < virtualNodeNum; j++) {
                // TODO CONTAIN KEY CHECKING
                t.put(nodeHashing.hash("actual-" + i + "-virtual-" + j), node);
            }
        }
        this.virtualNodes = t;

    }


    public T getVirtualNode(String key) {
//        virtualNodes.tailMap(distructHashing.)
        SortedMap<Long, T> tail = virtualNodes.tailMap(keyHashing.hash(key));
        if (tail.size() == 0) {
            return virtualNodes.get(virtualNodes.firstKey());
        }
        return tail.get(tail.firstKey());
    }


    public List<T> getNodes() {
        return nodes;
    }


}
