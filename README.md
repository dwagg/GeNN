
Copyright (c) 2016 David Wagg

This program is released under the "GNU GPL v3" License 
Please see the file License in this distribution for license terms

# GeNN
Open Source Neural Network Generator

Ideally GeNN Will be able to take arbitrary input and build an artificial neural network (ANN) based on minimal user inputs. A user would need to enter the hyper parameters of the ANN like the size of the output, the size of the hidden layers and how many hidded layers there are. Currently generating the ANN works well but there is a problem in the implementation of the Back Propagation algorithm using gradient descent. 

The Goal of GeNN is to have a ANN interface there is very easy to pick up and plug data into it only needing to specify the shape of the network and letting it be concerned with the shape of the data. The current state is that it only works as a classifier, thus restricting the data allowed, and it is currently build specifically to take the MNist contained. 

For more information or for bugs contact dwagg@pdx.edu
