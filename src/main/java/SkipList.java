import java.util.Random;

class SkipListNode {
    int val;
    SkipListNode[] forwards;

    public SkipListNode(int val, int level) {
        this.val = val;
        this.forwards = new SkipListNode[level];
    }
}

public class SkipList {
    private static final int MAX_LEVEL = 16;
    private static final double PROBABILITY = 0.5;

    private int levelCount = 1;
    private SkipListNode head = new SkipListNode(-1, MAX_LEVEL);

    // 插入元素
    public void insert(int val) {
        int level = randomLevel();
        SkipListNode newNode = new SkipListNode(val, level);

        // 更新跳跃表的层数
        if (level > levelCount) {
            level = ++levelCount;
        }

        // 从最高层开始，逐层查找插入位置
        SkipListNode current = head;
        for (int i = level - 1; i >= 0; i--) {
            while (current.forwards[i] != null && current.forwards[i].val < val) {
                current = current.forwards[i];
            }
            newNode.forwards[i] = current.forwards[i];
            current.forwards[i] = newNode;
        }
    }

    // 查找元素
    public boolean search(int target) {
        SkipListNode current = head;
        for (int i = levelCount - 1; i >= 0; i--) {
            while (current.forwards[i] != null && current.forwards[i].val < target) {
                current = current.forwards[i];
            }
            if (current.forwards[i] != null && current.forwards[i].val == target) {
                return true;
            }
        }
        return false;
    }

    // 删除元素
    public void delete(int target) {
        SkipListNode current = head;
        for (int i = levelCount - 1; i >= 0; i--) {
            while (current.forwards[i] != null && current.forwards[i].val < target) {
                current = current.forwards[i];
            }
            if (current.forwards[i] != null && current.forwards[i].val == target) {
                current.forwards[i] = current.forwards[i].forwards[i];
            }
        }
    }

    // 生成随机层数
    private int randomLevel() {
        int level = 1;
        Random random = new Random();
        while (random.nextDouble() < PROBABILITY && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    // 打印跳跃表
    public void printSkipList() {
        for (int i = levelCount - 1; i >= 0; i--) {
            SkipListNode current = head;
            System.out.print("Level " + i + ": ");
            while (current.forwards[i] != null) {
                System.out.print(current.forwards[i].val + " -> ");
                current = current.forwards[i];
            }
            System.out.println("null");
        }
    }

    public static void main(String[] args) {
        SkipList skipList = new SkipList();
        skipList.insert(3);
        skipList.insert(6);
        skipList.insert(2);
        skipList.insert(8);
        skipList.insert(4);
        skipList.insert(5);

        skipList.printSkipList();

        System.out.println("Search 6: " + skipList.search(6)); // 输出 true
        System.out.println("Search 7: " + skipList.search(7)); // 输出 false

        skipList.delete(6);
        skipList.delete(4);

        skipList.printSkipList();
    }
}