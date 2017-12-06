/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Iterator;

/**
 *
 * @author User
 */

public class Apply<InT, OutT> implements Iterator<OutT>
{
    private final ApplyFunction<InT, OutT> f;
    private final Iterator<InT> input;
    public Apply(Iterator<InT> input, ApplyFunction<InT, OutT> f)
    {
        this.f = f;
        this.input = input;
    }

    @Override
    public boolean hasNext()
    {
        return input.hasNext();
    }
    
    @Override
    public OutT next()
    {
        InT i = input.next();
        return f.apply(i);
    }
}
