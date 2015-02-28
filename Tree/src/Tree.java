import java.util.*;
import java.util.List;


public class Tree {
    public List<Tree> childs;
    private Vector currentVertex;
    private static final int RADIUS = 10;
    /* Радиус влияния (выбираем ближайшие) */
    private static final double D_I = 5;

    /* Радиус узла */
    private static final double D = 0.01;
    /* Радиус уничтожения */
    private static final double D_K = 4 * D;
    private static final int MAGIC_COUNT = 500;
    private static class TreeNear {
        Tree node;
        HashSet<Vector> near;
        Vector summary;

        public TreeNear(Tree node) {
            this.node = node;
            near = new HashSet<Vector>();
            summary = new Vector(0, 0, 0);
        }
        public void addPoint(Vector a) {
            near.add(a);
        }
        public void addVector(Vector a) {
            summary = summary.add(a.sub(node.currentVertex).norm());
        }
        public void subVector(Vector a) {
            summary = summary.sub(a.sub(node.currentVertex).norm());
        }
    }

    public static Tree createTree(int n) {
        Tree tree = new Tree(0, 0, 0);
        int lastDelete = 0;
        int currentDelete = 0;
        TreeNear tn = new TreeNear(tree);
        List<Vector> currentPoint = Generate.generatePoints(RADIUS, n);
        boolean flag = true;
        List<TreeNear> treeNode = new ArrayList<TreeNear>();
        treeNode.add(tn);
        HashMap<Vector, TreeNear> nearestNode = new HashMap<Vector, TreeNear>();
        TreeNear[] nearest = new TreeNear[currentPoint.size()];
        for (int i = 0; i < currentPoint.size(); i++) {
            Vector p = currentPoint.get(i);
            double curMin = p.dist(tn.node.currentVertex);
            if (curMin < D_I * D_I) {
                tn.addPoint(p);
                tn.addVector(p);
                nearest[i] = tn;
            }
        }
        while (flag && currentPoint.size() > 0) {
            System.out.println(currentPoint.size() +" " + treeNode.size());
            flag = false;
            /* Здесь находим для каждой точки ближайший Node и записываем в Node ближайшие к нему точки .... пункт b*/
           //это метод updateNear..
            /*c and d and e*/
           List<TreeNear> newTreeNode = new ArrayList<TreeNear>();
            for (TreeNear i : treeNode) {
                Vector curVector = new Vector(0, 0, 0);
                curVector = i.summary;
                /*
                for (Vector j : i.near) {
                    curVector = curVector.add(j.sub(i.node.currentVertex).norm());
                } */
                //i.near.clear();
                if (curVector.sqLength() == 0) {
                    continue;
                }
                flag = true;
                curVector = curVector.norm();
                Tree t = new Tree(curVector.mul(D).add(i.node.currentVertex));

                i.node.childs.add(t);
                TreeNear q = new TreeNear(t);
                newTreeNode.add(q);
                updateNearNode(nearest, q, i, currentPoint);
            }
            treeNode.addAll(newTreeNode);
            /* f and g */
            Iterator<Vector> it = currentPoint.iterator();
            currentDelete++;
            while (it.hasNext()) {
                Vector p = it.next();
                boolean delete = false;
                for (TreeNear i : newTreeNode/*treeNode*/) {
                    if (D_K * D_K >= p.dist(i.node.currentVertex)) {
                        delete = true;
                        break;
                    }
                }
                if (delete) {
                    it.remove();
                    lastDelete = currentDelete;
                }
            }
            if (currentDelete - lastDelete > MAGIC_COUNT) {
                break;
            }


        }
        return tree;
    }
    private Tree(int x, int y, int z) {
        currentVertex = new Vector(x, y, z);
        childs = new ArrayList<Tree>();
    }

    public Tree(Vector a) {
        currentVertex = a;
        childs = new ArrayList<Tree>();
    }
    public Vector getCurrentVertex() {
        return currentVertex;
    }

    private static void updateNearNode(TreeNear[] nearest, TreeNear t,TreeNear parent, List<Vector> currentPoint) {
        /*
        HashSet<Vector> e = new HashSet<Vector>(parent.near);
        for (Vector p : e) {            ;
            double curMin = p.dist(t.node.currentVertex);
            double hasMin = 0;
            if (nearest[p.number] != null) {
                hasMin = p.dist(nearest[p.number].node.currentVertex);
            }
            else {
                hasMin = D_K * 2;
            }
            if (curMin < hasMin && curMin < D_I * D_I) {
                t.addPoint(p);
                t.addVector(p);
                if (nearest[p.number] != null) {
                    nearest[p.number].near.remove(p);
                    nearest[p.number].subVector(p);
                }
                nearest[p.number] = t;
            }


        }*/
        for (int i = 0; i < currentPoint.size(); i++) {
            Vector p = currentPoint.get(i);
            double curMin = p.dist(t.node.currentVertex);
            double hasMin = 0;
            if (nearest[p.number] != null) {
                hasMin = p.dist(nearest[p.number].node.currentVertex);
            }
            else {
                hasMin = D_K * 2;
            }
            if (curMin < hasMin && curMin < D_I * D_I) {
                t.addPoint(p);
                t.addVector(p);
                if (nearest[p.number] != null) {
                	nearest[p.number].near.remove(p);
                    nearest[p.number].subVector(p);
                }
                nearest[p.number] = t;
            }


        }
    }

}
