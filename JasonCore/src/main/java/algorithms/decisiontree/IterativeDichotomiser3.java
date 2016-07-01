package algorithms.decisiontree;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

public class IterativeDichotomiser3 {

	/**
	 * 计算熵值
	 * @param dataSet 数据集
	 * @return 熵值
	 */
	public float CalcEntropy(ArrayList<Object[]> dataSet){
		try {
			int len = dataSet.size();
			Map<Object, Integer> k = new HashMap<>();
			for (Object[] objects : dataSet) {
				Object o = objects[objects.length - 1];
				if(!k.keySet().contains(o)){
					k.put(o, 0);
				}
				k.put(o, k.get(o) + 1);
			}
			float e = 0;
			for (Entry<Object, Integer> d : k.entrySet()) {
				float p = (float)d.getValue()/len;
				e -= p * (Math.log(p)/Math.log(2));	
			}
			return e;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 拆分数据集
	 * @param dataset 数据集
	 * @param axis 属性所在列
	 * @param value 属性值
	 * @return 符合属性等于value的子数据集
	 */
	public ArrayList<Object[]> SplitDataSet(ArrayList<Object[]> dataset, int axis, Object value){
		ArrayList<Object[]> r = new ArrayList<>();
		for (Object[] objects : dataset) {
			if(objects[axis].equals(value)){
				Object[] s = new Object[objects.length - 1];
				for (int i = 0,j = 0; i < objects.length; i++) {
					if(i!=axis){
						s[j] = objects[i];
						j++;
					}
				}
				r.add(s);
			}
		}
		return r;
	}
	
	/**
	 * 选择熵值最小或信息增益最大的属性进行数据集拆分
	 * 理解：某一列的可能值越多且对应的结果情况越多、(两列概率计算即熵值计算越大)说明该列不确定性较大、
	 * 不确定性越大越不能作为关键特性去决策、应该放到最下层
	 * 熵值计算公式：g(x)=-P(x)logP(x) (底为2，P(X) <= 1) 为单调递增函数
	 * @param dataset
	 * @return
	 */
	public int ChooseBestFeatureToSplit(ArrayList<Object[]> dataset){
		float baseE= CalcEntropy(dataset);
		int bestF = -1;
		float bestIG = 0;
		//属性列个数
		int featureCount = dataset.get(0).length;
		int len = dataset.size();
		for (int i = 0; i < featureCount - 1; i++) {
			//获取某一属性的不同值及相同值的个数
			Map<Object,Integer> featureValue = new HashMap<>();
			for (Object[] objects : dataset) {
				if(!featureValue.containsKey(objects[i])){
					featureValue.put(objects[i], 0);
				}
				featureValue.put(objects[i], featureValue.get(objects[i]) + 1);
			}
			float featureE = 0;
			for (Entry<Object,Integer> fv : featureValue.entrySet()) {
				float tempE = CalcEntropy(SplitDataSet(dataset, i, fv.getKey()));
				//某一属性值的概率乘以该值的熵
				featureE +=  tempE * ((float)fv.getValue()/len);	
			}
			//(熵是无序性（或不确定性）的度量指标)
			//信息熵(Information Gain)越大、即属性的熵值越小，即该属性的影响的不确定性越小、所以更加能够确定值
			//所以决策树的最上层节点应该是信息熵最大值。
			float tempIg = baseE - featureE;
			if(tempIg > bestIG){
				bestIG = tempIg;
				bestF = i;
			}
		}
		return bestF;
	}
	
	/**
	 * 构造决策树
	 * @param dataset 数据集
	 * @param labels 属性集合键值对
	 * @param parentNode 父节点
	 * @return
	 */
	public Node<String> CreateTree(ArrayList<Object[]> dataset, Map<Integer, String> labels, Node<String> parentNode){
		ArrayList<Object> classifiedTarget = new ArrayList<>();
		for (Object[] objects : dataset) {
			classifiedTarget.add(objects[objects.length - 1]);
		}
		//如果某个子数据集的目标结果都一样、则返回
		if(IsCompletedSame(classifiedTarget)){
			Node<String> node = new Node<String>(parentNode, classifiedTarget.get(0).toString(), null);
			return node;
		}
		//如果所有列属性都处理完、则选择最大可能性的值并返回
		if(dataset.get(0).length == 1){
			Map<Object, Integer> classCount = new HashMap<>();
			for (Object object : classifiedTarget) {
				if(!classCount.containsKey(object)) classCount.put(object, 0);
				classCount.put(object, classCount.get(object) + 1);
			}
			Entry<Object, Integer> m = null;
			Integer t = 0;
			for (Entry<Object, Integer> s : classCount.entrySet()) {
				if(s.getValue() > t){
					t = s.getValue();
					m = s;
				}
			}
			return new Node<String>(parentNode, m.getKey().toString(), null);
		}
		int bestF = ChooseBestFeatureToSplit(dataset);
		String label = labels.get(bestF);
		Node<String> node = new Node<String>(parentNode == null ? null : parentNode, label, null);
		node.child = new ArrayList<>();
		labels.remove(bestF);
		Set<Object> featureValues = GetFeatureValues(dataset,bestF).keySet();
		for (Object object : featureValues) {
			Node<String> child = CreateTree(SplitDataSet(dataset, bestF, object), labels, node);
			node.child.add(child);
		}
		return node;
	}
	
	/**
	 * 判断ArrayList集合所有值都一样
	 * @param al
	 * @return
	 */
	private boolean IsCompletedSame(ArrayList<Object> al){
		if(al == null || al.size() <= 1) return true;
		Object c = al.get(0);
		Iterator<Object> iterator = al.iterator();
		while (iterator.hasNext()) {
			Object object = (Object) iterator.next();
			if(!object.equals(c)) return false;
		}
		return true;
	}
	
	/**
	 * 获取某一属性所有可能值及同一值的个数
	 * @param dataset 数据集
	 * @param axis 属性索引即指定列
	 * @return
	 */
	private Map<Object,Integer> GetFeatureValues(ArrayList<Object[]> dataset, int axis){
		ArrayList<Object> classifiedTarget = new ArrayList<>();
		for (Object[] objects : dataset) {
			classifiedTarget.add(objects[axis]);
		}
		//获取某一属性的不同值及相同值的个数
		Map<Object, Integer> featureValue = new HashMap<>();
		for (Object objects : classifiedTarget) {
			if(!featureValue.containsKey(objects)){
				featureValue.put(objects, 0);
			}
			featureValue.put(objects, featureValue.get(objects) + 1);
		}
		return featureValue;
	}
	/**
	 * 决策树节点
	 * @author JasonChen1
	 *
	 * @param <E>
	 */
	private class Node<E> {
        E item;
        java.util.List<Node<E>> child;
        Node<E> parent;

        Node(Node<E> parent, E element, java.util.List<Node<E>> child) {
            this.item = element;
            this.child = child;
            this.parent = parent;
        }
    }
	
	@Test
	public void TestM3(){
		String[] property1 = {"sunny","hot","high","FALSE","no"};
		String[] property2 = {"sunny","hot","high","TRUE","no"};
		String[] property3 = {"overcast","hot","high","FALSE","yes"};
		String[] property4 = {"rainy","mild","high","FALSE","yes"};
		String[] property5 = {"rainy","cool","normal","FALSE","yes"};
		String[] property6 = {"rainy","cool","normal","TRUE","no"};
		String[] property7 = {"overcast","cool","normal","TRUE","yes"};
		String[] property8 = {"sunny","mild","high","FALSE","no"};
		String[] property9 = {"sunny","cool","normal","FALSE","yes"};
		String[] property10 = {"rainy","mild","normal","FALSE","yes"};
		String[] property11 = {"sunny","mild","normal","TRUE","yes"};
		String[] property12 = {"overcast","mild","high","TRUE","yes"};
		String[] property13 = {"overcast","hot","normal","FALSE","yes"};
		String[] property14 = {"rainy","mild","high","TRUE","no"};
		
		
		ArrayList<Object[]> dataset = new ArrayList<Object[]>();
		dataset.add(property1);
		dataset.add(property2);
		dataset.add(property3);
		dataset.add(property4);
		dataset.add(property5);
		dataset.add(property6);
		dataset.add(property7);
		dataset.add(property8);
		dataset.add(property9);
		dataset.add(property10);
		dataset.add(property11);
		dataset.add(property12);
		dataset.add(property13);
		dataset.add(property14);
		Map<Integer, String> labels = new HashMap<>();
		labels.put(0, "outlook");
		labels.put(1, "temperature");
		labels.put(2, "humidity");
		labels.put(3, "windy");
		labels.put(4, "play");
		Node<String> decisionTree = CreateTree(dataset, labels, null);
		System.out.println(decisionTree);
	}
	

	
	@Test
	public void TestM(){
		String[] property1 = {"1","1","yes"};
		String[] property2 = {"1","1","yes"};
		String[] property3 = {"1","0","no"};
		String[] property4 = {"0","1","no"};
		String[] property5 = {"0","1","no"};
		
		
		ArrayList<Object[]> dataset = new ArrayList<Object[]>();
		dataset.add(property1);
		dataset.add(property2);
		dataset.add(property3);
		dataset.add(property4);
		dataset.add(property5);
		float e = CalcEntropy(dataset);
		System.out.println(e);
		ArrayList<Object[]> subDataSet1 = SplitDataSet(dataset, 0, "1");
		e = CalcEntropy(subDataSet1);
		System.out.println(e);
		ArrayList<Object[]> subDataSet2 = SplitDataSet(dataset, 0, "0");
		e = CalcEntropy(subDataSet2);
		System.out.println(e);
		int bestfeature = ChooseBestFeatureToSplit(dataset);
		System.out.println(bestfeature);
	}
	
	@Test
	public void TestM2(){
		String[] property1 = {"sunny","hot","high","FALSE","no"};
		String[] property2 = {"sunny","hot","high","TRUE","no"};
		String[] property3 = {"overcast","hot","high","FALSE","yes"};
		String[] property4 = {"rainy","mild","high","FALSE","yes"};
		String[] property5 = {"rainy","cool","normal","FALSE","yes"};
		String[] property6 = {"rainy","cool","normal","TRUE","no"};
		String[] property7 = {"overcast","cool","normal","TRUE","yes"};
		String[] property8 = {"sunny","mild","high","FALSE","no"};
		String[] property9 = {"sunny","cool","normal","FALSE","yes"};
		String[] property10 = {"rainy","mild","normal","FALSE","yes"};
		String[] property11 = {"sunny","mild","normal","TRUE","yes"};
		String[] property12 = {"overcast","mild","high","TRUE","yes"};
		String[] property13 = {"overcast","hot","normal","FALSE","yes"};
		String[] property14 = {"rainy","mild","high","TRUE","no"};
		
		
		ArrayList<Object[]> dataset = new ArrayList<Object[]>();
		dataset.add(property1);
		dataset.add(property2);
		dataset.add(property3);
		dataset.add(property4);
		dataset.add(property5);
		dataset.add(property6);
		dataset.add(property7);
		dataset.add(property8);
		dataset.add(property9);
		dataset.add(property10);
		dataset.add(property11);
		dataset.add(property12);
		dataset.add(property13);
		dataset.add(property14);
		int bestfeature = ChooseBestFeatureToSplit(dataset);
		System.out.println(bestfeature);
	}
	
	@Test
	public void TestNg(){
		ArrayList<Object> test = new ArrayList<>();
		test.add(1);
		test.add(1);
		test.add(2);
	}
}
