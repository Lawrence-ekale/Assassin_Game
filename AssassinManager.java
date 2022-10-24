import java.util.*;
public class AssassinManager{
	List<AssassinNode> currentlyAlive;
	List<AssassinNode> dead;
	AssassinManager(List<String> names) throws IllegalArgumentException
	{
		currentlyAlive = new ArrayList<AssassinNode>();
		dead = new ArrayList<AssassinNode>();
		for(int i=0;i<names.size();i++)
		{
			
			if(currentlyAlive.size()<=0 && names.size()>=2)
			{
				AssassinNode temp = new AssassinNode(names.get(i),new AssassinNode(names.get(i+1)));
				currentlyAlive.add(temp);
			}else if(currentlyAlive.size()<=0 && names.size()==1)
			{
				AssassinNode temp = new AssassinNode(names.get(i),new AssassinNode(names.get(i)));
				currentlyAlive.add(temp);
			}else
			{
				if(i<names.size()-1)
				{
					if(i+1<=names.size()-1)//look-ahead if the node to be passed as next has its  next node
					{
						AssassinNode temp = new AssassinNode(names.get(i),new AssassinNode(names.get(i+1)));
						currentlyAlive.add(temp);

					}
				}else
				{
					AssassinNode temp = new AssassinNode(names.get(i),new AssassinNode(names.get(0)));
					currentlyAlive.add(temp);
				}
			}
		}
	}

	public void printKillRing()
	{
		for (int i=0;i<currentlyAlive.size();i++) {
			System.out.println("    "+currentlyAlive.get(i).name+" is stalking "+currentlyAlive.get(i).next.name);
		}
	}

	public void printGraveyard()
	{
		if(dead.size()>0)
			for (int i=dead.size()-1;i>=0;i--) {
				System.out.println("    "+dead.get(i).name+" was killed by "+dead.get(i).killer);
			}
	}

	public boolean killRingContains(String name)
	{
		boolean result = false;
		for(int i=0;i<currentlyAlive.size();i++)
		{
			if(currentlyAlive.get(i).name.equalsIgnoreCase(name))
			{
				result=true;
				i=currentlyAlive.size();
			}
		}

		return result;
	}

	public boolean graveyardContains(String name)
	{
		boolean result = false;
		for(int i=0;i<dead.size();i++)
		{
			if(dead.get(i).name.equalsIgnoreCase(name))
			{
				result=true;
				i=dead.size();
			}
		}

		return result;
	}

	public boolean gameOver()
	{
		boolean result=false;
		if(currentlyAlive.size()==1)
			result=true;
		return result;
	}

	public String winner()
	{
		if(gameOver())
			return currentlyAlive.get(0).name;
		else return null;
	}

	public void kill(String name)
	{
		if(gameOver())
			throw new IllegalStateException();

		if(!killRingContains(name))
			 throw new IllegalArgumentException();

		for(int i=0;i<currentlyAlive.size();i++)
		{
			if(currentlyAlive.get(i).name.equalsIgnoreCase(name))
			{
				AssassinNode temp =currentlyAlive.get(i);
				if(i!=0)
					temp.killer = currentlyAlive.get(i-1).name;
				else temp.killer = currentlyAlive.get(currentlyAlive.size()-1).name;
				temp.next=null;
				currentlyAlive.remove(i);
				if(currentlyAlive.size()!=1)
				{
					if(i!=0){
						if(i!=currentlyAlive.size())
							currentlyAlive.get(i-1).next = currentlyAlive.get(i);
						else currentlyAlive.get(i-1).next = currentlyAlive.get(0);
					}
					else currentlyAlive.get(currentlyAlive.size()-1).next = currentlyAlive.get(i);
				}else{
					currentlyAlive.get(0).next = currentlyAlive.get(0);
				}
				
				dead.add(temp);
			}
		}
	}
}