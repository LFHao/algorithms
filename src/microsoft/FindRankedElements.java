package microsoft;

public class FindRankedElements {
    RankNode root = null;

    void put(int number){
        if (root == null){
            root = new RankNode(number);
        } else {
            root.insert(number);
        }
    }

    int get(int number){
        return root.getRank(number);
    }

    public class RankNode {
        public int left_size = 0;
        public RankNode left, right;
        public int data;
        public RankNode(int d){
            data = d;
        }

        public void insert(int d){
            if (d < data){
                if (left != null) left.insert(d);
                else left = new RankNode(d);
                left_size++;
            }
            else {
                if (right != null) right.insert(d);
                else right = new RankNode(d);
            }
        }

        public int getRank(int d){
            if (d == data){
                return left_size;
            }
            else if (d < data){
                if (left == null) return -1;
                else return left.getRank(d);
            }
            else {
                int right_rank = right == null ? -1 : right.getRank(d);
                if (right_rank == -1) return -1;
                else return left_size + 1 + right_rank;
            }
        }
    }

    public static void main(String[] args) {
        FindRankedElements s = new FindRankedElements();
        s.put(1);
        s.put(2);
        s.put(3);
        s.put(4);
        s.put(5);
        s.put(7);
        System.out.println(s.get(2));
        System.out.println(s.get(7));
        s.put(6);
        System.out.println(s.get(7));
    }

}
