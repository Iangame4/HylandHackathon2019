namespace BlackMirrorServer.Models
{
    public class User
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Photo { get; set; }
        public string Email { get; set; }
        public string Password { get; set; }
        public float Average_Rating { get; set; }
        public int Count_Ratings { get; set; }
    }
}