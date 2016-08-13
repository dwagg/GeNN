
Copyright (c) 2016 David Wagg

This program is released under the "GNU GPL v3" License 
Please see the file License in this distribution for license terms

# GeNN
Open Source Neural Network Generator

Ideally GeNN Will be able to take arbitrary input and build an artificial neural network (ANN) based on minimal user inputs. A user would need to enter the hyper parameters of the ANN like the size of the output, the size of the hidden layers and how many hidded layers there are. Currently generating the ANN works well but there is a problem in the implementation of the Back Propagation algorithm using gradient descent. I have cut out the hidden layers for the mean time and giving a few minutes of training time the program is recognizing nearly 70% of handwritten digits correctly.

The Goal of GeNN is to have a ANN interface there is very easy to pick up and plug data into it only needing to specify the shape of the network and letting it be concerned with the shape of the data. The current state is that it only works as a classifier, thus restricting the data allowed, and it is currently build specifically to take the MNist contained. 

The only thing needed to build this that is different from building a Java Hello world program is adding the la4j jar that is in the resources folder to the classpath. 

To build the project you just need to run the command:
	javac -cp resources/la4j-0.6.0.jar:. basic/*.java
	
To run the project run the command:
	java -cp resources/la4j-0.6.0.jar:. basic/Main
	
The data that I used in working on this project can be found at (mnist_train.csv, mnist_test.csv):
	http://pjreddie.com/projects/mnist-in-csv/
By placing those files into the dummy folder in the GeNN/src folder you can use that data to run the tests for yourself.

Thanks to:
	https://mattmazur.com/2015/03/17/a-step-by-step-backpropagation-example/ for helping with back propagation understanding
	http://pjreddie.com/projects/mnist-in-csv/ for extracting the mnist data to a usable format saving alot of time!!
	la4j.org for the linear algebra library that is relied upon heavily throughout.
For more information or for bugs contact dwagg@pdx.edu
