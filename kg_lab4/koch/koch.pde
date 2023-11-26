void kochCurve(float x1, float y1, float x2, float y2, int order) {
  if (order == 0) {
    line(round(x1), round(y1), round(x2), round(y2));
    return;
  }
  
  float Lx = (x2 - x1) / 3.0;
  float Ly = (y2 - y1) / 3.0;
  
  float Bx = x1 + Lx;
  float By = y1 + Ly;
  float Dx = Bx + Lx;
  float Dy = By + Ly;
  
  float Cx = (Bx + Dx) / 2;
  Cx -= (1 / 2) * (Dx - Bx);
  Cx += (sqrt(3) / 2) * (Dy - By);
  
  float Cy = (By + Dy) / 2;
  Cy -= (sqrt(3) / 2) * (Dx - Bx);
  Cy += (1 / 2) * (Dy - By);
  
  kochCurve(x1, y1, Bx, By, (order - 1));
  kochCurve(Bx, By, Cx, Cy, (order - 1));
  kochCurve(Cx, Cy, Dx, Dy, (order - 1));
  kochCurve(Dx, Dy, x2, y2, (order - 1));
}

void setup() {
  size(1280, 720);
  background(255);
  
  kochCurve(350, 150, 750, 150, 4);
  kochCurve(750, 150, 550, 490, 4);
  kochCurve(550, 490, 350, 150, 4);
}
