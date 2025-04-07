import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authToken = localStorage.getItem('authToken');
  console.log('Interceptor ejecutado. Token:', authToken);
  if (authToken) {
    console.log('Interceptor ejecutado. Token:', authToken);
    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${authToken}`,
      },
    });
    return next(authReq);
  }
  return next(req);
};
