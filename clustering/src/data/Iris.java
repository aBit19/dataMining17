package data;


public class Iris {

	float sepalLength;
	float sepalWidth;
	float petalLength;
	float petalWidth;
	IrisClass Class;
	
	public Iris(float sepal_length, float sepal_width, float petal_length, float petal_width, String iris_class) {
		this(sepal_length, sepal_width, petal_length, petal_width, ResolveIrisClass(iris_class));
	}
	
	public Iris(float sepal_length, float sepal_width, float petal_length, float petal_width, IrisClass iris_class) {
		this.sepalLength = sepal_length;
		this.sepalWidth = sepal_width;
		this.petalLength = petal_length;
		this.petalWidth = petal_width;
		this.Class = iris_class;
	}
	
	private static IrisClass ResolveIrisClass(String iris_class) {
		if(iris_class.equals("Iris-setosa"))
			return IrisClass.Iris_setosa;
		if(iris_class.equals("Iris-versicolor"))
			return IrisClass.Iris_versicolor;
		return IrisClass.Iris_virginica;
	}

	public Iris getMean(Iris iris) {
        return new Iris((sepalLength + iris.sepalLength)/2,
                        (sepalWidth + iris.sepalWidth)/2,
                        (petalLength + iris.petalLength)/2,
                        (petalWidth + iris.petalWidth)/2,
                        Class);
    }

    public double getDistanceFrom(Iris iris) {
	    return Math.sqrt(dist(sepalLength, iris.sepalLength) +
                         dist(sepalWidth, iris.sepalWidth) +
                         dist(petalLength, iris.petalLength) +
                         dist(petalWidth, iris.sepalWidth));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Iris ))
            return false;
        Iris ir = (Iris) o;
        return Class.equals(ir.Class)
                && Float.compare(sepalLength, ir.sepalLength) == 0
                && Float.compare(sepalWidth, ir.sepalWidth) == 0
                && Float.compare(petalLength, ir.petalLength) == 0
                && Float.compare(petalWidth, ir.petalWidth) == 0 ;
    }

    private static float dist(float x, float y) {
        return (float) Math.pow(x - y, 2);
    }

    @Override
	public String toString() {
		String result = "Iris Object --> | Sepal_Length = "+this.sepalLength+" | Sepal_Width = "+this.sepalWidth+" | Petal_Length = "+this.petalLength + " | Petal_Width = "+this.petalWidth + " | Class = "+this.Class;
		
		return result;
	}
}
