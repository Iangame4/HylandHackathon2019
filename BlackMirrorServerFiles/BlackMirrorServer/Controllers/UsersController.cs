using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using BlackMirrorServer.Models;

namespace BlackMirrorServer.Controllers
{
    public class UsersController : ApiController
    {
        static readonly IUserRepository repository = new UserRepository();

        public IEnumerable<User> GetAllUsers()
        {
            return repository.GetAll();
        }

        public User GetUser(int id)
        {
            User item = repository.Get(id);
            if (item == null)
            {
                throw new HttpResponseException(HttpStatusCode.NotFound);
            }
            return item;
        }

        //public IEnumerable<User> GetUsersByCategory(string category)
        //{
        //    return repository.GetAll().Where(
        //        p => string.Equals(p.Category, category, StringComparison.OrdinalIgnoreCase));
        //}

        public HttpResponseMessage PostProduct(User item)
        {
            item = repository.Add(item);
            var response = Request.CreateResponse<User>(HttpStatusCode.Created, item);

            string uri = Url.Link("DefaultApi", new { id = item.Id });
            response.Headers.Location = new Uri(uri);
            return response;
        }

        public void PutProduct(int id, User user)
        {
            user.Id = id;
            if (!repository.Update(user))
            {
                throw new HttpResponseException(HttpStatusCode.NotFound);
            }
        }

        public void DeleteUser(int id)
        {
            repository.Remove(id);
        }
    }
}
