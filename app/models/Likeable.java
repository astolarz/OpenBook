package models;

import java.util.*;

import javax.persistence.*;

import controllers.Comments;

import play.db.jpa.*;
import play.data.validation.*;

@Entity
public abstract class Likeable extends Model {
  
  @OneToMany(mappedBy="parentObj", cascade=CascadeType.ALL)
  public List<Likes> likes;
  
  public Date date;
  
  public Likeable addLike(User user){
	  Likes newLike = new Likes(this, user).save();
	  this.likes.add(newLike);
	  this.save();
	  return this;
	}
  
  public void addLike (Likes l){
    likes.add(l);
    this.save();
  }
  
  public void removeLike(Likes l){
    likes.remove(l);
    l.delete();
    this.save();
  }
  
  public boolean currentUserLiked (){
    User currentUser = Comments.user();
    return Likes.find("author = ? AND parentObj = ?", currentUser,this).first() != null;
  }
  
}
