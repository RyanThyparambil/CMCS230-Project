public class BinarySearchTree {
    private TreeNode root;
    public BinarySearchTree(){
        root=null;
    }
    public void add(Patient pat){
        if(pat==null){return;}
        root=addNode(root, new TreeNode(pat));
    }
    public Patient find(PatientIdentity i){
        TreeNode node=findNode(root,i);
        if(node!=null){
            return node.patient;
        }
        return null;
    }
    private TreeNode addNode(TreeNode r, TreeNode n){
        if(r==null){
            return n;
        }
        PatientIdentity rID=r.patient.getPatientIdentity();
        PatientIdentity nID=n.patient.getPatientIdentity();
        if(nID.isLessThan(rID)){
            r.left=addNode(r.left,n);
        }
        else if(rID.isLessThan(nID)){
            r.right=addNode(r.right,n);
        }
        return r;
    }

    private TreeNode findNode(TreeNode root, PatientIdentity ID){
        if(root==null){
            return null;
        }
        PatientIdentity rootID=root.patient.getPatientIdentity();
        if(ID.match(rootID)){
            return root;
        }
        if(ID.isLessThan(rootID)){
            return findNode(root.left,ID);
        }
        return findNode(root.right,ID);
    }

    private class TreeNode{
        public Patient patient;
        public TreeNode left;
        public TreeNode right;
        public TreeNode(Patient pat){
            patient=pat;
            left=null;
            right=null;
        }
    }

}