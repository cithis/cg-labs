void fern(int its) {
  float[] coords = {1, 0};
  float old_x;
  int r = 80;
  
  for (int i = 0; i < its; i++) {
    old_x = coords[0];
    int cookie_for_ruby = (int) random(0, 100.1);
    if (cookie_for_ruby <= 85) {
      coords[0] = 0.85 * coords[0] + 0.04 * coords[1];
      coords[1] = -0.04 * old_x + 0.85 * coords[1] + 1.6;
    } else if (cookie_for_ruby <= 92) {
      coords[0] = 0.25 * coords[0] - 0.26 * coords[1];
      coords[1] = 0.23 * old_x + 0.25 * coords[1] + 1.6;
    } else if (cookie_for_ruby <= 99) {
      coords[0] = -0.15 * coords[0] + 0.3 * coords[1];
      coords[1] = 0.26 * old_x + 0.2 * coords[1] + 0.44;
    } else {
      coords[0]  = 0;
      coords[1] *= 0.16;
    }
    
    int hue = 360 * cookie_for_ruby / 100;
    colorMode(HSB, 360, 100, 100);
    set((int) (400 + r * coords[0]), (int) (850 - r * coords[1]), color(hue, 90, 90));
  }
}

void setup() {
  size(1000, 1000);
  background(255);
  
  fern((int) pow(10, 5));
}
