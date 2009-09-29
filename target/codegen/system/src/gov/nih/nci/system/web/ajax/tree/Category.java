package gov.nih.nci.system.web.ajax.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 */
public class Category
{

    public static Category getById(long id)
    {
        return (Category)catMap.get(new Long(id));
    }

    public Category(long id, String name, String packageName, Category children[])
    {
        this.id = id;
        this.name = name;
        this.packageName = packageName;
        this.children = new ArrayList();
        Category arr[] = children;
        int len = arr.length;
        for(int i = 0; i < len; i++)
        {
            Category child = arr[i];
            this.children.add(child);
        }

        catMap.put(new Long(id), this);
    }
    
    public Category(long id, String name, String packageName, List children)
    {
        this.id = id;
        this.name = name;
        this.packageName = packageName;        
        this.children = children;

        catMap.put(new Long(id), this);
    }    

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List getChildren()
    {
        return children;
    }

    public void setChildren(List children)
    {
        this.children = children;
    }

    public void toggle()
    {
        toggle = !toggle;
    }

    public boolean isToggle()
    {
        return toggle;
    }

    private static Map catMap = new HashMap();
    private long id;
    private String packageName;
    private String name;
    private List children;
    private boolean toggle;

    static 
    {
        new Category(1L, "Root", "", new Category[] {
            new Category(2L, "Java", "", new Category[] {
                new Category(3L, "Web Frameworks", "", new Category[] {
                    new Category(4L, "Struts", "", new Category[0]), new Category(7L, "Stripes", "", new Category[0]), new Category(8L, "Rife", "", new Category[0])
                }), new Category(9L, "Persistence", "", new Category[] {
                    new Category(10L, "iBatis", "", new Category[0]), new Category(11L, "Hibernate", "", new Category[0]), new Category(12L, "JDO", "", new Category[0]), new Category(13L, "JDBC", "", new Category[0])
                })
            }), new Category(14L, "JavaScript", "", new Category[] {
                new Category(15L, "Dojo", "", new Category[0]), new Category(16L, "Prototype", "", new Category[0]), new Category(17L, "Scriptaculous", "", new Category[0]), new Category(18L, "OpenRico", "", new Category[0]), new Category(19L, "DWR", "", new Category[0])
            })
        });
    }

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public String toString() {
		return 	"Category id: " + this.id + " ||\n " +
				"Category name: " + this.name + " ||\n " +
				"Category packageName: " + this.packageName;
	}
}

