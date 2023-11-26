float cabs(float[] a) {
  return sqrt(pow(a[0], 2) + pow(a[1], 2));
}

float[] cmul(float[] a, float[] b) {
  float[] res = {-1, -1};
  res[0] = (a[0] * b[0]) - (a[1] * b[1]);
  res[1] = (a[1] * b[0]) + (a[0] * b[1]);
  
  return res;
}

float[] cadd(float[] a, float[] b) {
  float[] res = {a[0] + b[0], a[1] + b[1]};
  
  return res;
}

int mandelbrot(float[] c, int max_iter) {
  float[] z = {0, 0};
  int n;
  for (n = 0; n < max_iter && cabs(z) <= 2; n++)
    z = cadd(cmul(z, z), c);
  
  return n;
}

void mandelplot(int w, int h) {
  int[] Re = {-2, 1};
  int[] Im = {-1, 1};
  
  for (int i = 0; i < w; i++) {
    for (int j = 0; j < h; j++) {
      float real = Re[0] + (i / (float) w) * (Re[1] - Re[0]);
      float imag = Im[0] + (j / (float) h) * (Im[1] - Im[0]);
      float[] c  = {real, imag};
      
      int m  = mandelbrot(c, 120);
      if (m == 120) {
        set(i, j, #000000);
        continue;
      } else {
        int hue = 256 * m / 120;
        colorMode(HSB, 360, 100, 100);
        set(i, j, color(hue, 90, 90));
      }
    }
  }
}

void setup() {
  size(1280, 720);
  background(255);
  
  mandelplot(1280, 720);
}
