package com.capston.iceamericano.smartcampus.Trilaterlation;

/**
 * Created by 경남 on 2018-05-18.
 */

import com.lemmingapex.trilateration.*;
import com.lemmingapex.trilateration.LinearLeastSquaresSolver;
import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;

import static junit.framework.Assert.assertEquals;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer.Optimum;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.SingularMatrixException;

/**
 * Test class which is initialized with different predefined test cases.
 * Test was refactored from @author scott
 *
 * @author burfi
 */
public class TrilaterationTest {

    private double[][] positions;
    private double[] distances;
    private double[] expectedPosition;
    private double acceptedDelta;
    private StringBuilder output;

    private RealVector linearCalculatedPosition;
    private Optimum nonLinearOptimum;
    private String realOutput;

    public TrilaterationTest(double[][] positions, double[] distances) {
        this.positions = positions;
        this.distances = distances;
        testCase();
        realOutput = outputResult().toString();
        //compareExpectedAndCalculatedResults();
    }

    public double[] returnOutput(){
        return nonLinearOptimum.getPoint().toArray();
    }

    private void testCase() {
        com.lemmingapex.trilateration.TrilaterationFunction trilaterationFunction = new com.lemmingapex.trilateration.TrilaterationFunction(positions, distances);
        //com.lemmingapex.trilateration.LinearLeastSquaresSolver lSolver = new LinearLeastSquaresSolver(trilaterationFunction);
        com.lemmingapex.trilateration.NonLinearLeastSquaresSolver nlSolver = new NonLinearLeastSquaresSolver(trilaterationFunction, new LevenbergMarquardtOptimizer());

        //linearCalculatedPosition = lSolver.solve();
        nonLinearOptimum = nlSolver.solve();
    }

    private StringBuilder outputResult() {
        output = new StringBuilder();

//        printDoubleArray("expectedPosition: ", expectedPosition);
//        printDoubleArray("linear calculatedPosition: ", linearCalculatedPosition.toArray());
        printDoubleArray("non-linear calculatedPosition: ", nonLinearOptimum.getPoint().toArray());
//        output.append("numberOfIterations: ").append(nonLinearOptimum.getIterations()).append("\n");
//        output.append("numberOfEvaluations: ").append(nonLinearOptimum.getEvaluations()).append("\n");
//        try {
//            RealVector standardDeviation = nonLinearOptimum.getSigma(0);
//            printDoubleArray("standardDeviation: ", standardDeviation.toArray());
//            output.append("Norm of deviation: ").append(standardDeviation.getNorm()).append("\n");
//            RealMatrix covarianceMatrix = nonLinearOptimum.getCovariances(0);
//            output.append("covarianceMatrix: ").append(covarianceMatrix).append("\n");
//        } catch (SingularMatrixException e) {
//            System.err.println(e.getMessage());
//        }

        //System.out.println(output.toString());

        return output;
    }

    private void compareExpectedAndCalculatedResults() {
        double[] calculatedPosition = nonLinearOptimum.getPoint().toArray();
        for (int i = 0; i < calculatedPosition.length; i++) {
            assertEquals(expectedPosition[i], calculatedPosition[i], acceptedDelta);
        }
    }

    private void printDoubleArray(String tag, double[] values) {
        output.append(tag);
        for (double p : values) {
            output.append(String.format("%.0f",p)).append(" ");
        }
        output.append("\n");
    }
}