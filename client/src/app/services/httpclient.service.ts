import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {UserCreateDto} from "../models/person/PersonCreateDTO";


@Injectable({
  providedIn: 'root'
})
export class HttpClientService {
  constructor(private httpClient: HttpClient) {}

  getProducts() {
    // return this.httpClient.get<Product[]>('http://localhost:8080/data/products');
  }

  public deleteProduct(id: number) {
    return this.httpClient.patch(
      'http://localhost:8080/data/products/delete?ID=' + id.toString(), {}
    );
  }


  getProfile() {
    return this.httpClient.get('http://localhost:8080/data/users/user?email='
      + sessionStorage.getItem('username'));
  }

  // updateProfile(user: User) {
  //   return this.httpClient.patch(
  //     'http://localhost:8080/data/users/update?id='
  //     + sessionStorage.getItem('userId'),
  //     user);
  // }

  deleteAccount() {
    return this.httpClient.patch('http://localhost:8080/data/users/delete?id='
      + sessionStorage.getItem('userId'), {}
    );
  }

  registerUser(user: UserCreateDto) {
    return this.httpClient.post('http://localhost:8083/register', user);
  }

  // completeOrder(order: OrderCreateDto) {
  //   return this.httpClient.post('http://localhost:8080/data/orders', order);
  // }

  // addProduct(product: Product) {
  //   return this.httpClient.post('http://localhost:8080/data/products', product);
  // }
  //
  // editProduct(product: Product) {
  //   return this.httpClient.patch('http://localhost:8080/data/products/update?id=' + product.id, product);
  // }

  // getUsers() {
  //   return this.httpClient.get<User[]>('http://localhost:8080/data/users/all');
  // }

  deleteUser(id: string) {
    return this.httpClient.patch('http://localhost:8080/data/users/delete?id='
      + id, {}
    );
  }

}
