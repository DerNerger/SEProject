package main;
import java.util.Random;


public class RandomNoise {

	static double[][] generateWhiteNoise(int width, int height) {
		Random random = new Random();
		double[][] noise = new double[width][height];
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				noise[i][j] = random.nextDouble() % 1;
			}
		}
		
		return noise;
	}
	
	/*
	 * TODO: create noise on the fly instead of pregenerating array
	 */
	static double[][] generateSmoothNoise(double[][] baseNoise, int octave) {
		int width = baseNoise.length;
		int height = baseNoise[0].length;
		
		double[][] smoothNoise = new double[width][height];
		
		int samplePeriod = 1 << octave; // 2 ^ octave
		double sampleFrequency = 1d / samplePeriod;
		
		for (int i = 0; i < width; i++) {
			int sample_i0 = (i / samplePeriod) * samplePeriod; // largest multiple of samplePeriod smaller than i
			int sample_i1 = (sample_i0 + samplePeriod) % width;
			double horizontal_blend = (i - sample_i0) * sampleFrequency;
			
			for (int j = 0; j < height; j++) {
				int sample_j0 = (j / samplePeriod) * samplePeriod;
				int sample_j1 = (sample_j0 + samplePeriod) % height;
				
				double vertical_blend = (j - sample_j0) * sampleFrequency;
				
				// blend top two corners
				double top = interpolate(baseNoise[sample_i0][sample_j0], baseNoise[sample_i1][sample_j0], horizontal_blend);
				
				// blend bottom two corners
				double bottom = interpolate(baseNoise[sample_i0][sample_j1], baseNoise[sample_i1][sample_j1], horizontal_blend);
				
				// final blend
				smoothNoise[i][j] = interpolate(top, bottom, vertical_blend);
			}
		}
		return smoothNoise;
	}
	
	/*
	 * The closer alpha is to 0, the closer the value is to x0
	 * The closer alpha is to 1, the closer the value is to x1
	 */
	static double interpolate(double x0, double x1, double alpha) {
		return x0 * (1 - alpha) + alpha * x1;
	}
	
	static double[][] getNoise(int width, int height, int octaveCount, double persistance) {
		double[][] baseNoise = generateWhiteNoise(width, height);
		
		double[][][] smoothNoise = new double[octaveCount][][];
		
		// generate smooth noise
		for (int i = 0; i < octaveCount; i++) {
			smoothNoise[i] = generateSmoothNoise(baseNoise, i);
		}
		
		double[][] perlinNoise = new double[width][height];
		double amplitude = 1;
		double totalAmplitude = 0;
		
		for (int octave = octaveCount - 1; octave >= 0; octave--) {
			amplitude *= persistance;
			totalAmplitude += amplitude;
			
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
				}
			}
		}
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				perlinNoise[i][j] /= totalAmplitude;
			}
		}
		
		return perlinNoise;
	}

}
