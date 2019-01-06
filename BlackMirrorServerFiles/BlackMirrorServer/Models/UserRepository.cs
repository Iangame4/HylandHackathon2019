using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace BlackMirrorServer.Models
{
    public class UserRepository : IUserRepository
    {
        private List<User> users = new List<User>();
        private int _nextId = 1;

        public UserRepository()
        {
            // Create some initial users
            Add(new User
            {
                Name = "Alex",
                Photo = "https://s3.amazonaws.com/codecademy-content/courses/web-101/web101-image_brownbear.jpg",
                Email = "alex@gg.com",
                Password = "Admin1",
                Average_Rating = 3.7F,
                Count_Ratings = 1
            });
            Add(new User
            {
                Name = "Dan",
                Photo = "http://vemco.com/wp-content/uploads/2012/09/image-banner2.jpg",
                Email = "dan@gg.com",
                Password = "Admin1",
                Average_Rating = 5.0F,
                Count_Ratings = 1
            });
            Add(new User
            {
                Name = "Ian",
                Photo = "https://i.ytimg.com/vi/N_8EDyjOA80/maxresdefault.jpg",
                Email = "ian@gg.com",
                Password = "Admin1",
                Average_Rating = 4.0F,
                Count_Ratings = 1
            });
        }

        public IEnumerable<User> GetAll()
        {
            return users;
        }

        public User Get(int id)
        {
            return users.Find(p => p.Id == id);
        }

        public User Add(User item)
        {
            if (item == null)
            {
                throw new ArgumentNullException("item");
            }

            item.Id = _nextId++;
            users.Add(item);
            return item;
        }

        public void Remove(int id)
        {
            users.RemoveAll(p => p.Id == id);
        }

        // Broken
        public bool Update(User item)
        {
            if (item == null)
            {
                throw new ArgumentNullException("item");
            }
            int index = users.FindIndex(p => p.Id == item.Id);
            if (index == -1)
            {
                return false;
            }
            users.RemoveAt(index);
            users.Add(item);
            return true;
        }
    }
}